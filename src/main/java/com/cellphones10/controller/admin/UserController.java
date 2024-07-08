package com.cellphones10.controller.admin;

import com.cellphones10.dto.ProductDTO;
import com.cellphones10.dto.RoleDTO;
import com.cellphones10.dto.UserDTO;
import com.cellphones10.dto.output.Output;
import com.cellphones10.entity.User;
import com.cellphones10.service.impl.RoleService;
import com.cellphones10.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/admin/user")
public class UserController {
    @Autowired private UserService userService ;
    @Autowired private RoleService roleService;
    @PutMapping("/resetpassword")
    public ResponseEntity resetPasswordUser (@RequestBody()  UserDTO userDTO)
    {
        if(userService.resetPassword(userDTO.getUsername()))
        {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/detail/{id}")
    public UserDTO  getUserDetail(@PathVariable Long id)
    {
        return  userService.findUserById(id);
    }
    @PostMapping
    public UserDTO addUser (@RequestBody() UserDTO userDTO)
    {
        return userService.save(userDTO);
    }
    @GetMapping
    public Output<UserDTO> getAllUser (@RequestParam("page")int page,@RequestParam("limit")int limit)
    {
        Pageable pageable = PageRequest.of(page-1, limit);
        Output<UserDTO> output = new Output<>();
        output.setListResult(userService.findAll(pageable));

        output.setPage(page);
        Long count = userService.count();
        output.setTotalItems(count);
        Integer remainpage =(int) (count-(page*limit));
        if(remainpage < 0)
        {
            remainpage = 0;
        }
        output.setRemainingproduct(remainpage);

        return output;

    }
    @GetMapping("/getall/role")
    public List<RoleDTO> getAllRole ()
    {
        return  roleService.findAllRole();

    }
    @PutMapping("/update/{id}")
    public ResponseEntity updateUser(@RequestBody() UserDTO userDTO, @PathVariable Long id){
        if(userService.update(userDTO, id))
        {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id)
    {   if(userService.delete(id))
    {
        return new  ResponseEntity(HttpStatus.OK);
    }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
