/**
 * Rest controller for catalog request.
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.web.controller;

import de.thbingen.epro.project.web.services.CatalogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/catalog")
public class CatalogController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(CatalogController.class);

    //TODO implement interface
    private CatalogService catalogService;

    public ResponseEntity<?> getCatalog() {
        LOG.debug("GET: /v2/catalog getCatalog()");
        return ResponseEntity.ok(catalogService.getCatalog());
    }

}
