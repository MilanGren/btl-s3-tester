package cz.isfgroup;

// https://www.devglan.com/spring-boot/spring-boot-file-upload-download

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AppController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FrontaMapper frontaMapper;

    @PostMapping("/receiveEdidListByBtl")
    public String receiveEdidListByBtl() {

        return "OK";
    }

    // ja pres rest obdrzim multipartfile, davkaid, noderef
    // metoda Post
    @PostMapping("/receiveFileFromRepo")
    public String receiveFileFromRepo(@RequestParam(value = "noderef") String noderef, // muze projit jen prazdna noderef
                                      @RequestParam(value = "davkaid") String davkaid,
                                      @RequestParam(value = "edid") String edid,
                                      @RequestParam(value = "ecgfile") MultipartFile ecgfile,  // mozna by bylo lepsi posilat jen ecg nodeRef
                                      @RequestParam(value = "configfile") MultipartFile configfile  // mozna by bylo lepsi toto dostavat jako nodeRef
    ) {

        System.out.println(noderef);
        System.out.println(davkaid);

        // ecgfile

        // zde pridam do fronty, ulozim soubory na disk..

        // vezmu interim soubor a poslu dal
        return "ok";
    }

    @GetMapping(value = "/pullFileByBtl1", produces = MediaType.APPLICATION_PDF_VALUE)
    public @ResponseBody
    byte[] pullFileByBtl1(@RequestParam String path) {

        frontaMapper.insert(new Fronta(Long.valueOf(999), "NODEREF", "edid1", "davka1", "NEW"));
        frontaMapper.insert(new Fronta(Long.valueOf(999), "NODEREF", "edid1", "davka2", "NEW"));
        frontaMapper.insert(new Fronta(Long.valueOf(999), "NODEREF", "edid2", "davka2", "NEW"));
        System.out.println(frontaMapper.getAll());

        try {
            return Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @RequestMapping(value = "/pullFileByBtl2", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void pullFileByBtl2(@RequestParam String path, HttpServletResponse response) throws IOException {
        String test = "new string test bytes";
        response.setHeader("Content-Disposition", "attachment; filename=file.txt");
        response.getOutputStream().write(test.getBytes());
    }

    @GetMapping(value = "/pullFileByBtl3", produces = "application/zip")
    public void pullFileByBtl3(@RequestParam String path, HttpServletResponse response) throws IOException {
        System.out.println("OK");
        String zipFileName = "MYZIP";
        ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream());

        FileSystemResource resource = new FileSystemResource(path);
        ZipEntry zipEntry = new ZipEntry(resource.getFilename());
        zipEntry.setSize(resource.contentLength());
        zipOut.putNextEntry(zipEntry);
        StreamUtils.copy(resource.getInputStream(), zipOut);
        zipOut.closeEntry();

        zipOut.finish();
        zipOut.close();
        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + zipFileName + "\"");
        System.out.println("IM OK");
    }

    @PostMapping("/receiveFileFromBtl")
    public String receiveFileFromBtl(@RequestParam("file") MultipartFile file,
                                     @RequestParam(value = "davkaid") String davkaid) {

        // zistam novy interim soubor a davkaid. Podle davkaid najdu nodeRef

        // odeberu z fronty

        // zmeni status na cekam na prijeti od alfreska

        // odeslu do alfreska pomoci vytvoreneho endpointu - odeslu (interim, nazev configuraku, davkaid)

        return "ok";

    }


}
