package br.com.estoque.consumer.controller;

import br.com.estoque.consumer.model.User;
import br.com.estoque.consumer.repository.UserRepository;
import br.com.estoque.consumer.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository repo;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public void update(Long id, User user) {
        if(!repo.existsById(id)){
            logger.info("Não foi encontrado esse ID na Base de Dados.");
        }
        user.setUserId(id);
        User userAtualizado = userService.salvar(user);
        logger.info("User atualizado com sucesso.");
    }

    public void insert(User user) {
        repo.save(user);
        logger.info("User adicionado com sucesso.");
    }

    public void delete(Long id) {
        if(!repo.existsById(id)){
            logger.info("Não foi encontrado esse ID na Base de Dados.");
        }
        else{
            userService.excluir(id);
            logger.info("User deletado com sucesso.");
        }
    }
}
