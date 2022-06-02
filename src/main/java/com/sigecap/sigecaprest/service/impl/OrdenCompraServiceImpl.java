package com.sigecap.sigecaprest.service.impl;

import com.sigecap.sigecaprest.exception.BusinessException;
import com.sigecap.sigecaprest.model.Archivo;
import com.sigecap.sigecaprest.model.ComprobantePago;
import com.sigecap.sigecaprest.model.OrdenCompra;
import com.sigecap.sigecaprest.model.dto.OrdenCompraRequestDTO;
import com.sigecap.sigecaprest.model.dto.OrdenCompraResponseDTO;
import com.sigecap.sigecaprest.model.dto.SolicitudInscripcionDTO;
import com.sigecap.sigecaprest.repository.ArchivoRepository;
import com.sigecap.sigecaprest.repository.ComprobantePagoRepository;
import com.sigecap.sigecaprest.repository.OrdenCompraRepository;
import com.sigecap.sigecaprest.repository.jdbc.OrdenCompraJdbcRepository;
import com.sigecap.sigecaprest.service.ArchivoService;
import com.sigecap.sigecaprest.service.OrdenCompraService;
import com.sigecap.sigecaprest.util.BusinessMsgError;
import com.sigecap.sigecaprest.util.Constantes;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service("ordenCompraService")
public class OrdenCompraServiceImpl implements OrdenCompraService {

    @Value("${URL_PATH_BASE_ATTACHMENT}")
    private String URL_PATH_BASE_ATTACHMENT;

    @Autowired
    private OrdenCompraJdbcRepository ordenCompraJdbcRepository;

    @Autowired
    private OrdenCompraRepository ordenCompraRepository;

    @Autowired
    private ComprobantePagoRepository comprobantePagoRepository;

    @Autowired
    private ArchivoRepository archivoRepository;

    @Autowired
    private ArchivoService archivoService;

    @Override
    public List<OrdenCompraResponseDTO> obtenerOrdenCompras(String ruc) throws Exception {

        if (ruc.length() != Constantes.TAMAÑO_RUC) {
            throw new BusinessException(BusinessMsgError.ERROR_RUC_INCORRECTO);
        }

        List<OrdenCompraResponseDTO> ordenes = ordenCompraJdbcRepository.obtenerDatos(ruc);

        if (ordenes == null || ordenes.size() == 0) {
            throw new BusinessException(BusinessMsgError.ERROR_NO_DATA_RUC);
        }

        ordenes.stream().forEach(x -> {
            try {
                x.setFileDocumento(convertToBase64(FileUtils.readFileToByteArray(new File(FilenameUtils.separatorsToSystem(x.getRutaArchivo())))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return ordenes;

    }

    @Override
    public String registrar(OrdenCompraRequestDTO request, MultipartFile[] files) throws Exception {



        if (ObjectUtils.isEmpty(request.getCodigoPago()) || ObjectUtils.isEmpty(request.getTipoDocumento()) || ObjectUtils.isEmpty(request.getSerie()) || ObjectUtils.isEmpty(request.getUsuario()) || ObjectUtils.isEmpty(request.getNumero()) ||
                ObjectUtils.isEmpty(request.getFechaEmision()) || ObjectUtils.isEmpty(request.getTotalValorVenta()) || ObjectUtils.isEmpty(request.getTotalIgvVenta()) || ObjectUtils.isEmpty(request.getTotalPrecioVenta())) {
            throw new BusinessException(BusinessMsgError.ERROR_DATOS_INCOMPLETOS);
        }

        String numeroSerie = request.getSerie().concat("-").concat(request.getNumero());

        if(numeroSerie.length() > Constantes.TAMAÑO_NUMERO_SERIE){
            throw new BusinessException(BusinessMsgError.ERROR_NUMERO_SERIE_LIMITE_CARACTERES);
        }

        if (request.getCodigoPago().length() != Constantes.TAMAÑO_CODIGO_PAGO) {
            throw new BusinessException(BusinessMsgError.ERROR_CODIGO_PAGO_LIMITE_CARACTERES);
        }

        valicacionArchivos(files);

        OrdenCompra o = ordenCompraRepository.findById(request.getCodigoPago()).orElse(null);

        if (o == null) {
            throw new BusinessException(BusinessMsgError.ERROR_CODIGO_PAGO_NO_EXISTE);
        }

        if (o.getInProcesadoFacturacion().equalsIgnoreCase(Constantes.ESTADO_PROCESADO_FACTURACION_SI)) {
            throw new BusinessException(BusinessMsgError.ERROR_CODIGO_PAGO_PROCESADO);
        }


        String pkComprobante = comprobantePagoRepository.generatePrimaryKey(Constantes.TABLE_COMPROBANTE_PAGO, Constantes.ID_TABLE_COMPROBANTE_PAGO);

        ComprobantePago comprobantePago = new ComprobantePago();
        comprobantePago.setIdComprobantePago(pkComprobante);
        comprobantePago.setIdTipoDocumento(String.valueOf(request.getTipoDocumento()));
        comprobantePago.setNumero(numeroSerie);
        comprobantePago.setFechaEmision(request.getFechaEmision());
        comprobantePago.setTotalValorVenta(request.getTotalValorVenta());
        comprobantePago.setTotalIgvVenta(request.getTotalIgvVenta());
        comprobantePago.setTotalPrecioVenta(request.getTotalPrecioVenta());
        comprobantePago.setEstado(Constantes.ESTADO_CREADO);
        comprobantePago.setFechaRegistro(new Date());
        comprobantePago.setIdUsuarioRegistro(request.getUsuario());

        comprobantePagoRepository.save(comprobantePago);
        ordenCompraRepository.actualizarFacturacion(request.getCodigoPago());

        List<SolicitudInscripcionDTO> solicitudes = ordenCompraJdbcRepository.obtenerDatosSolicitud(request.getCodigoPago());

        List<Archivo> archivosSave = new ArrayList<>();


        for (int i = 0; i < files.length; i++) {
            String pkArchivo = archivoRepository.generatePrimaryKey(Constantes.TABLE_ARCHIVO, Constantes.ID_TABLE_ARCHIVO);
            Archivo archivo = new Archivo();
            archivo.setIdArchivo(pkArchivo);
            archivo.setIdDocumento("CP-" + obtenerTipoDocumento(files[i].getContentType()) + "-" + pkComprobante);
            archivo.setNombre(files[i].getOriginalFilename());
            archivo.setTipo(files[i].getContentType());
            archivo.setPeso((files[i].getSize()) + " B");
            archivo.setFechaCreacion(new Date());
            archivo.setEstado(Constantes.ESTADO_ACTIVO);
            archivo.setFile(files[i]);
            this.archivoService.save(archivo);
            archivosSave.add(archivo);
        }

        for (SolicitudInscripcionDTO s : solicitudes) {
            for (Archivo a : archivosSave) {
                comprobantePagoRepository.actualizarSolicitud(pkComprobante,s.getSolicitudDetalle());
                String url = "CP/" + s.getSolicitud() + "/" + s.getSolicitudDetalle() + "/CP-" + request.getCodigoPago() + "/AR-" + a.getIdArchivo();
                saveFile(a.getFile(), url);
            }
        }

        return pkComprobante;
    }

    private void saveFile(MultipartFile file, String url) {

        try {
            Path path = Paths.get(URL_PATH_BASE_ATTACHMENT + url);
            boolean dirExist = Files.exists(path);
            if (!dirExist) {
                Files.createDirectories(path);
            }
            Path nameImage = Paths.get(file.getOriginalFilename());
            Path targetLocation = path.resolve(nameImage);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {

        }

    }

    private void valicacionArchivos(MultipartFile[] files) throws Exception {
        if (files.length != Constantes.TAMAÑO_NUMEROS_ARCHIVO) {
            throw new BusinessException(BusinessMsgError.ERROR_ARCHIVOS_INCOMPLETOS);
        }

        int contadorPDF = 0;
        int contadorXML = 0;
        int contadorZIP = 0;

        for (int i = 0; i < files.length; i++) {

            if(files[i].getContentType().equals("application/pdf") || files[i].getContentType().equals("application/xml") || files[i].getContentType().equals("application/zip")){
                if(files[i].getContentType().equals("application/pdf")){
                    contadorPDF++;
                }
                if(files[i].getContentType().equals("application/xml")){
                    contadorXML++;
                }
                if(files[i].getContentType().equals("application/zip")){
                    contadorZIP++;
                }
            }
            else{
                throw new BusinessException(BusinessMsgError.ERROR_ARCHIVO_FORMATO);

            }

            if(contadorPDF >1 || contadorXML>1 || contadorZIP>1){
                throw new BusinessException(BusinessMsgError.ERROR_ARCHIVO_CADA_UNO);
            }


            if (files[i].getSize() == 0 || files[i].getSize() > Constantes.TAMAÑO_ARCHIVO) {
                throw new BusinessException(BusinessMsgError.ERROR_ARCHIVOS_TAMAÑO.concat("-").concat(files[i].getOriginalFilename()));
            }

            if (files[i].getOriginalFilename().length() > Constantes.TAMAÑO_NOMBRE_ARCHIVO) {
                throw new BusinessException(BusinessMsgError.ERROR_ARCHIVO_NOMBRE_LIMITE_CARACTERES.concat("-").concat(files[i].getOriginalFilename()));
            }
        }


    }

    private String obtenerTipoDocumento(String contentType) {
        String type = "";
        switch (contentType) {
            case "application/pdf":
                type = "PDF";
                break;
            case "application/xml":
                type = "XML";
                break;
            case "application/zip":
                type = "ZIP";
                break;
            default:
                type = "OTH";
        }
        return type;
    }

    private String convertToBase64(byte[] url) {
        return Base64.getEncoder().encodeToString(url);
    }
}
