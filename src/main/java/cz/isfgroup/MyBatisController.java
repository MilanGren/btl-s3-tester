package cz.isfgroup;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MyBatisController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FrontaMapper frontaMapper;

    @PostMapping("/db")
    public void test() {

        System.out.println(frontaMapper.getAll());
        System.out.println("TEST DONE");
    }

    @PostMapping("/dbold")
    public void testOld() {

        System.out.println(frontaMapper.getAll());
        System.out.println("TEST DONE");
    }

}