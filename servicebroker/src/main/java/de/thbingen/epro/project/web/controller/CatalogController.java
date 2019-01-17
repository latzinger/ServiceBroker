/**
 * Rest controller for catalog request.
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CatalogController extends BaseController {

    public static final String PATH = "/v2/catalog";

    private static final Logger LOG = LoggerFactory.getLogger(CatalogController.class);

    @GetMapping(path = PATH)
    public ResponseEntity<?> getCatalog() {
        //TODO implement response => replace null with Catalog Object or method returning a Catalog Object
        LOG.debug("GET: /v2/catalog getCatalog()");
        return ResponseEntity.ok(null);
    }

}
