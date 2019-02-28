package de.thbingen.epro.project.web.controller;

import de.thbingen.epro.project.servicebroker.services.CatalogService;
import de.thbingen.epro.project.web.exception.InvalidApiVersionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Catalog Controller
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

@RestController
@RequestMapping("/v2/catalog")
@Slf4j
public class CatalogController extends BaseController {


    @Autowired
    private CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping(value = {"/", ""})
    public ResponseEntity<?> getCatalog(@RequestHeader HttpHeaders httpHeaders) throws InvalidApiVersionException {
        log.debug("GET: /v2/catalog getCatalog()");
        checkApiVersion(httpHeaders);
        return ResponseEntity.ok(catalogService.getCatalog());
    }

}
