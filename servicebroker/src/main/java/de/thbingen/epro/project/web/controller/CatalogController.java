/**
 * TODO add description
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.web.controller;

import de.thbingen.epro.project.servicebroker.services.CatalogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/catalog")
public class CatalogController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(CatalogController.class);

    @Autowired
    private CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping
    public ResponseEntity<?> getCatalog(@RequestHeader HttpHeaders httpHeaders) {
        LOG.debug("GET: /v2/catalog getCatalog()");
        checkApiVersion(httpHeaders);
        return ResponseEntity.ok(catalogService.getCatalog());
    }

}
