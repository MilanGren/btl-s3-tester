package cz.isfgroup.visitor;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.CONTINUE;

public class ImportStrategyV1 extends AbstractImportStrategy {

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attr) {

        setShift(dir);

        try {
            if (dir.getNameCount() == 3 + shift && !"Examinations".equals(dir.getFileName())) {
                return CONTINUE;
            }
            if (dir.getNameCount() >= 5 + shift) {
                messages.put(dir, "unexpected directory");
                return CONTINUE;
            }
            if (dirIsEmpty(dir)) {
                messages.put(dir, "empty directory");
                return CONTINUE;
            }
            if (filesCountInDir(dir) > 1) {
                messages.put(dir, "more than one files in directory");
                return CONTINUE;
            }
        } catch (IOException e) {
            messages.put(dir, "exception: " + e.getMessage());
        }
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path filePath,
                                     BasicFileAttributes attr) {

        // all files being formed by 3 folders and not having Examinations as third folder are not to be checked. For example rootFolder/db1/References/xxx is left wihout any check and skipped.
        if (filePath.getNameCount() > 3 + shift && !containsGivenName(filePath, "Examinations", 3 + shift)) {
            return CONTINUE;
        }
        if (filePath.getNameCount() != 5 + shift) {
            messages.put(filePath, "unexpected file");
            return CONTINUE;
        }
        if (attr.isSymbolicLink()) {
            messages.put(filePath, "is symbolic link");
            return CONTINUE;
        } else if (attr.isRegularFile()) {
            if (!isXml(filePath)) {
                messages.put(filePath, "has not .xml extension");
                return CONTINUE;
            }
        } else {
            messages.put(filePath, "is not regular file");
            return CONTINUE;
        }

        files.add(filePath.toFile());

        return CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir,
                                              IOException exc) {
        if (exc != null) {
            messages.put(dir, "directory visit failed - " + exc.getMessage());
        }
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file,
                                           IOException exc) {
        messages.put(file, "file visit failed - " + exc.getMessage());
        return CONTINUE;
    }

}
