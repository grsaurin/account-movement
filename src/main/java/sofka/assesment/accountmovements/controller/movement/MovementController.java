package sofka.assesment.accountmovements.controller.movement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import sofka.assesment.accountmovements.exceptions.UnableProcessMovementException;
import sofka.assesment.accountmovements.model.Movement;
import sofka.assesment.accountmovements.service.movement.MovementService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/movements")
public class MovementController {
    private final MovementService movementService;

    @Autowired
    public MovementController(MovementService movementService) {
        this.movementService = movementService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllMovements() {
        try{
            List<Movement> allMovements = movementService.getMovements();
            return new ResponseEntity(allMovements, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }  catch (HttpStatusCodeException e) {
            return new ResponseEntity(e.getMessage(), e.getStatusCode());
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getMovementById(@PathVariable("id") Long id) {
        try{
            Movement m = movementService.getMovement(id);
            return new ResponseEntity<>(m, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (HttpStatusCodeException e) {
            return new ResponseEntity(e.getMessage(), e.getStatusCode());
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createMovement(@RequestBody Movement movement) {
        try{
            movementService.addMovement(movement);
            return new ResponseEntity( HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }  catch (HttpStatusCodeException e) {
            return new ResponseEntity(e.getMessage(), e.getStatusCode());
        } catch (UnableProcessMovementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteMovementById(@PathVariable("id") Long id) {
        try{
            movementService.deleteMovement(id);
            return new ResponseEntity( HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }  catch (HttpStatusCodeException e) {
            return new ResponseEntity(e.getMessage(), e.getStatusCode());
        }
    }

    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateMovementById(@RequestBody Movement movement) {
        try{
            movementService.updateMovement(movement);
            return new ResponseEntity( HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }  catch (HttpStatusCodeException e) {
            return new ResponseEntity(e.getMessage(), e.getStatusCode());
        }
    }
}
