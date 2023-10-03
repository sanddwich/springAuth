package spring.auth.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

//@CrossOrigin(
//  exposedHeaders = {
//    "Access-Control-Allow-Origin"
//  },
//  origins = {
//    "http://localhost:3000"
//  })
@RestController
@RequestMapping("/api/v1/checkToken")
public class CheckToken {
    @GetMapping({"/", ""})
    public ResponseEntity index() throws Exception {
        Map<String,Object> response = new HashMap<>();

        response.put("result", true);
        return ResponseEntity.ok(response);
    }
}
