package cz.isfgroup;

// https://www.devglan.com/spring-boot/spring-boot-file-upload-download

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
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

    @Autowired
    private DavkaMapper davkaMapper;

    @Autowired
    private EcgMapper ecgMapper;

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

    @PostMapping("up-edid")
    //@AlfrescoTransaction
    //@AlfrescoAuthentication
    public ResponseEntity<Map<String, String>> upedid(@RequestBody LinkedHashMap<String, List<String>> body) {
        // TODO validace dotazu
        List<String> edidList = body.get("list");
        System.out.println(edidList);
        String davkaId = UUID.randomUUID().toString();
        DavkaMember davka = new DavkaMember(davkaId, "NEW");
        davkaMapper.insert(davka);
        frontaMapper.insertList(edidList.stream().map(
            edid -> new FrontaMember(UUID.randomUUID().toString(), edid, davka.getDavkaid(), "NEW", new Date())).collect(
            Collectors.toList()));
        return ResponseEntity.ok(Map.of("davkaId", davka.getDavkaid()));
    }

    @PostMapping("list-davky")
    public ResponseEntity<String> listDavky() {
        return ResponseEntity.ok(davkaMapper.getAll().stream().map(x -> x.toString()).collect(
            Collectors.joining("\n")));
    }

    @PostMapping("list-fronta")
    public ResponseEntity<String> listFronta() {

        String out = "";

        frontaMapper.getAllEdids().stream().forEach(edid ->
            {
                System.out.println(
                    edid + ": " + frontaMapper.getAllByEdid(edid).stream().map(x -> x.getDavkaid()).collect(Collectors.joining(" | ")));
            }
        );

        //frontaMapper.getAll().stream().map(x -> x.)

        return ResponseEntity.ok(frontaMapper.getAll().stream().map(x -> x.toString()).collect(
            Collectors.joining("\n")));
    }

    @PostMapping("populate")
    public String populate() {

        ecgMapper.insert(new Ecg("edid", "00-00-00", "site0"));

        List<Ecg> ecgs = new ArrayList<>();
        ecgs.add(new Ecg("edid0", "11-22-33", "site0"));
        ecgs.add(new Ecg("edid1", "11-22-33", "site0"));
        ecgMapper.insertMany(ecgs);
        System.out.println("TEST 1:");
        ecgMapper.getAll().stream().forEach(ecg -> System.out.println(ecg.getEdId()));
        System.out.println("");
        System.out.println("getByEdid: " + ecgMapper.getByEdid("edid0"));

        System.out.println("TEST 2:");
        List<String> edids = new ArrayList<>();
        edids.add("edid1");
        edids.add("edid0");
        ecgMapper.getByEdidList(edids).stream().forEach(ecg -> System.out.println("getByEdidList: " + ecg.getEdId()));

        System.out.println("TEST 3");
        List<Ecg> ary = new ArrayList<>();
        ary.add(new Ecg("edid0", "11-22-33", "site0"));
        ary.add(new Ecg("edid1", "11-22-33", "site0"));
        ecgMapper.getByEcgList(ary, "site0").stream().forEach(
                ecg -> System.out.println("edid: " + ecg.getEdId() + ", nodeRef: " + ecg.getNodeRef() + ", site: " + ecg.getSiteNodeRef())
        );

        System.out.println("TEST 4");
        davkaMapper.insert(new DavkaMember("davka1", "NEW"));
        davkaMapper.insert(new DavkaMember("davka2", "NEW"));
        List<DavkaMember> davky = new ArrayList<>();
        davky.add(new DavkaMember("davka3", "STATUS"));
        davky.add(new DavkaMember("davka4", "STATUS"));
        davkaMapper.insertMany(davky);
        System.out.println("davky:");
        davkaMapper.getAll().stream().forEach(davkaMember -> System.out.println(davkaMember.getDavkaid()));
        System.out.println("");

        frontaMapper.insert(new FrontaMember(Long.valueOf(999), "nodeRef1", "edid1", "davka1", "NEW", new Date()));

        String sDate1 = "31/12/1998";
        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        frontaMapper.insert(new FrontaMember(Long.valueOf(999), "nodeRef1", "edid1", "davka2", "NEW", date));
        frontaMapper.insert(new FrontaMember(Long.valueOf(999), "nodeRef2", "edid2", "davka2", "NEW", new Date()));

        System.out.println("fronta head: " + frontaMapper.getAllHead());

        System.out.println("fronta olders of head: " + frontaMapper.getOldestOfHead());

        return "OK";
    }


    @GetMapping(value = "/pullFileByBtl1", produces = MediaType.APPLICATION_PDF_VALUE)
    public @ResponseBody
    byte[] pullFileByBtl1(@RequestParam String path) {

        //System.out.println(frontaMapper.getAllByEdid("edid2"));

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
