package com.zorbatron.zbgt.api.worldgen;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.minecraftforge.fml.common.Loader;

import org.apache.commons.io.IOUtils;

import com.zorbatron.zbgt.api.util.ZBGTLog;

import gregtech.api.GTValues;
import gregtech.api.worldgen.config.WorldGenRegistry;

// More or less directly copied from zbgt:CEu
public class WorldGenRegister {

    public static void init() throws IOException {
        try {
            copyCustomConfigs();
        } catch (IOException exception) {
            ZBGTLog.logger.fatal("Failed to add ZBGT worldgen", exception);
        }
    }

    private static void copyCustomConfigs() throws IOException {
        Path configPath = Loader.instance().getConfigDir().toPath().resolve(GTValues.MODID);
        Path worldgenVeinRootPath = configPath.resolve("worldgen/vein");
        Path jarFileExtractLock = configPath.resolve("worldgen_extracted_zbgt.json");

        if (!Files.exists(worldgenVeinRootPath)) {
            Files.createDirectories(worldgenVeinRootPath);
        }

        if (!Files.exists(jarFileExtractLock) ||
                !Files.list(worldgenVeinRootPath).peek(path -> ZBGTLog.logger.info(path)).findFirst().isPresent()) {
            if (!Files.exists(jarFileExtractLock)) {
                Files.createFile(jarFileExtractLock);
                extractJarVeinDefinitions(configPath, jarFileExtractLock);
            }
            WorldGenRegister.extractJarVeinDefinitions(configPath, worldgenVeinRootPath);
        }
    }

    private static void extractJarVeinDefinitions(Path configPath, Path targetPath) throws IOException {
        // The path of the worldgen folder in the config folder
        Path worldgenRootPath = configPath.resolve("worldgen");
        // The path of the physical vein folder in the config folder
        Path oreVeinRootPath = worldgenRootPath.resolve("vein");
        // The path of the named dimensions file in the config folder
        Path dimensionsRootPath = configPath.resolve("dimensions.json");
        // The path of the lock file in the config folder
        Path extractLockPath = configPath.resolve("worldgen_extracted_zbgt.json");

        FileSystem zipFileSystem = null;
        try {
            URL sampleUrl = WorldGenRegistry.class.getResource("/assets/zbgt/");
            if (sampleUrl == null) throw new FileNotFoundException("Could not find .gtassetsroot");
            URI sampleUri = sampleUrl.toURI();
            // The Path for representing the worldgen folder in the assets folder in the Gregtech resources folder in
            // the jar
            Path worldgenJarRootPath;
            // The Path for representing the vein folder in the vein folder in the assets folder in the zbgt
            // resources folder in the jar
            Path oreVeinJarRootPath;

            if (sampleUri.getScheme().equals("jar") || sampleUri.getScheme().equals("zip")) {
                zipFileSystem = FileSystems.newFileSystem(sampleUri, Collections.emptyMap());
                worldgenJarRootPath = zipFileSystem.getPath("/assets/zbgt/worldgen");
                oreVeinJarRootPath = zipFileSystem.getPath("/assets/zbgt/worldgen/vein");
            } else if (sampleUri.getScheme().equals("file")) {
                URL url = WorldGenRegistry.class.getResource("/assets/zbgt/worldgen");
                if (url == null) throw new FileNotFoundException("Could not find /assets/zbgt/worldgen");
                worldgenJarRootPath = Paths.get(url.toURI());

                url = WorldGenRegistry.class.getResource("/assets/zbgt/worldgen/vein");
                if (url == null) throw new FileNotFoundException("Could not find /assets/zbgt/worldgen/vein");
                oreVeinJarRootPath = Paths.get(url.toURI());
            } else {
                throw new IllegalStateException(
                        "Unable to locate absolute path to worldgen root directory: " + sampleUri);
            }

            // Attempts to extract the worldgen definition jsons
            if (targetPath.compareTo(oreVeinRootPath) == 0) {
                ZBGTLog.logger.info("Attempting extraction of standard worldgen definitions from {} to {}",
                        oreVeinJarRootPath, oreVeinRootPath);
                // Find all the default worldgen files in the assets folder
                List<Path> jarFiles;
                try (Stream<Path> stream = Files.walk(oreVeinJarRootPath)) {
                    jarFiles = stream.filter(Files::isRegularFile).collect(Collectors.toList());
                }

                // Replaces or creates the default worldgen files
                for (Path jarFile : jarFiles) {
                    Path worldgenPath = oreVeinRootPath.resolve(oreVeinJarRootPath.relativize(jarFile).toString());
                    Files.createDirectories(worldgenPath.getParent());
                    Files.copy(jarFile, worldgenPath, StandardCopyOption.REPLACE_EXISTING);
                }
                ZBGTLog.logger.info("Extracted {} builtin worldgen vein definitions into vein folder", jarFiles.size());
            }

            // Attempts to extract the named dimensions json folder
            else if (targetPath.compareTo(dimensionsRootPath) == 0) {
                ZBGTLog.logger.info("Attempting extraction of standard dimension definitions from {} to {}",
                        worldgenJarRootPath, dimensionsRootPath);

                Path dimensionFile = worldgenJarRootPath.resolve("dimensions.json");

                Path worldgenPath = dimensionsRootPath
                        .resolve(worldgenJarRootPath.relativize(worldgenJarRootPath).toString());
                Files.copy(dimensionFile, worldgenPath, StandardCopyOption.REPLACE_EXISTING);

                ZBGTLog.logger.info("Extracted builtin dimension definitions into worldgen folder");
            }
            // Attempts to extract lock txt file
            else if (targetPath.compareTo(extractLockPath) == 0) {
                Path extractLockFile = worldgenJarRootPath.resolve("worldgen_extracted_zbgt.json");

                Path worldgenPath = extractLockPath
                        .resolve(worldgenJarRootPath.relativize(worldgenJarRootPath).toString());
                Files.copy(extractLockFile, worldgenPath, StandardCopyOption.REPLACE_EXISTING);

                ZBGTLog.logger.info("Extracted jar lock file into worldgen folder");
            }

        } catch (URISyntaxException impossible) {
            // this is impossible, since getResource always returns valid URI
            throw new RuntimeException(impossible);
        } finally {
            if (zipFileSystem != null) {
                // close zip file system to avoid issues
                IOUtils.closeQuietly(zipFileSystem);
            }
        }
    }
}
