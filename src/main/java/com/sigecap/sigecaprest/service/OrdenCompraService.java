package com.sigecap.sigecaprest.service;

import com.sigecap.sigecaprest.model.dto.OrdenCompraRequestDTO;
import com.sigecap.sigecaprest.model.dto.OrdenCompraResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface OrdenCompraService {

    List<OrdenCompraResponseDTO> obtenerOrdenCompras(String ruc) throws Exception;

    String registrar(OrdenCompraRequestDTO request, MultipartFile[] files) throws Exception;
}
