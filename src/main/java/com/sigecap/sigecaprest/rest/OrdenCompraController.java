package com.sigecap.sigecaprest.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sigecap.sigecaprest.exception.BusinessException;
import com.sigecap.sigecaprest.model.dto.OrdenCompraRequestDTO;
import com.sigecap.sigecaprest.model.dto.OrdenCompraResponseDTO;
import com.sigecap.sigecaprest.service.OrdenCompraService;
import com.sigecap.sigecaprest.util.RespuestaApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/orden-compra")
public class OrdenCompraController {

    @Autowired
    private OrdenCompraService ordenCompraService;

    @GetMapping(value = "/find/{ruc}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrdenCompraResponseDTO>> buscarPorId(@PathVariable String ruc) throws Exception{
        try {
            return new ResponseEntity<List<OrdenCompraResponseDTO>>(ordenCompraService.obtenerOrdenCompras(ruc), HttpStatus.OK);
        }
        catch (BusinessException b){
            throw new BusinessException(b.getMessage());
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/save", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity registrar(@RequestParam(value="files") MultipartFile[] files, @RequestParam(value="orden") String orden) throws Exception {
        try {
            ObjectMapper obj = new ObjectMapper();
            OrdenCompraRequestDTO o = obj.readValue(orden, OrdenCompraRequestDTO.class);
            String codigo = ordenCompraService.registrar(o,files);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (BusinessException b){
            throw new BusinessException(b.getMessage());
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
