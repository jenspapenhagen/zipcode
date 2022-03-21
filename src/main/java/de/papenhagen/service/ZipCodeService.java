package de.papenhagen.service;

import de.papenhagen.enities.CSVInput;
import io.quarkus.cache.CacheResult;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Slf4j
@ApplicationScoped
public class ZipCodeService {

    public static final String PATH_TO_CSV = "PLZ.csv";

    private static final long LOWEST_ZIPCODE = 1623L;
    private static final long HIGHEST_ZIPCODE = 99998L;

    public Optional<CSVInput> filterByZipcode(final long zipCode) {
        if (zipCode < LOWEST_ZIPCODE || zipCode > HIGHEST_ZIPCODE) {
            return Optional.empty();
        }
        final Map<Long, CSVInput> csvInputMap = readCSV();
        final CSVInput csvInput = csvInputMap.get(zipCode);

        return isNull(csvInput) ? Optional.empty() : Optional.of(csvInput);
    }

    @CacheResult(cacheName = "zipcode-cache")
    public Map<Long, CSVInput> readCSV() {
        try {
            //read the csv
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PATH_TO_CSV);
            if (isNull(inputStream)) {
                return Collections.emptyMap();
            }
            final String readFile = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            //split by Line and then parse into pojo
            final List<String> splitByLine = Arrays.stream(readFile.split("\\r?\\n")).toList();
            return splitByLine.stream()
                    .map(line -> line.split(";"))
                    .map(split -> CSVInput.builder()
                            .zipCode(Integer.parseInt(split[0].replaceAll("\"", "")))
                            .state(split[1].replaceAll("\"", ""))
                            .lon(Double.parseDouble(split[2].replaceAll("\"", "")))
                            .lat(Double.parseDouble(split[3].replaceAll("\"", "")))
                            .build())
                    //map the List of CSVInput into a Map for faster filter
                    .collect(Collectors.toMap(CSVInput::getZipCode, Function.identity()));
        } catch (IOException e) {
            log.error("IOException on reading the csv file");
            e.printStackTrace();
        }

        return Collections.emptyMap();
    }

}
