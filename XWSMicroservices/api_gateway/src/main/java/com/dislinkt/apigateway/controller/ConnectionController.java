package com.dislinkt.apigateway.controller;


import com.dislinkt.apigateway.dto.ConnectionDTO;
import com.dislinkt.apigateway.dto.NewUserDTO;
import com.dislinkt.apigateway.service.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/connections")
public class ConnectionController {
    @Autowired
    private ConnectionService connectionService;

    @PostMapping("/addConnection")
    public ResponseEntity<Boolean> saveConnection(@RequestBody ConnectionDTO connection){
        return new ResponseEntity<Boolean>(connectionService.createConnection(connection), HttpStatus.CREATED);
    }

    @GetMapping("/getConnections/{userId}")
    public ResponseEntity<?> getConnections(@PathVariable("userId") long userId){
        return new ResponseEntity<>(connectionService.getConnections(userId), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{connectionId}")
    public ResponseEntity<?> deleteConnection(@PathVariable("connectionId") long connectionId){
        System.out.println("dobijeni id:" + connectionId);
        return new ResponseEntity<>(connectionService.deleteConnection(connectionId), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateConnection(@RequestBody ConnectionDTO connection){
        return new ResponseEntity<>(connectionService.updateConnection(connection), HttpStatus.OK);
    }


}
