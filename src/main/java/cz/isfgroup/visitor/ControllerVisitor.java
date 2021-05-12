package cz.isfgroup.visitor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;

@RestController
@EnableAsync
@RequiredArgsConstructor
@Slf4j
public class ControllerVisitor {

    public void visit0() {
        AbstractImportStrategy validator = new ImportStrategyV0();
        Path startingDir = Paths.get("rootV0");
        try {
            Files.walkFileTree(startingDir, new HashSet<>(), 5, validator);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(validator.get());
    }

    public void visit1() {
        AbstractImportStrategy validator = new ImportStrategyV1();
        Path startingDir = Paths.get("rootV1");
        try {
            Files.walkFileTree(startingDir, new HashSet<>(), 5, validator);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(validator.get());
    }

    public void visit2() {
        AbstractImportStrategy validator = new ImportStrategyV2();
        Path startingDir = Paths.get("rootV2");
        try {
            Files.walkFileTree(startingDir, new HashSet<>(), 5, validator);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(validator.get());
    }

    @PostMapping("/visitor")
    public String visitor() {
        //visit0();
        //visit1();
        visit2();
        return "ok";
    }
}
