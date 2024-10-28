package org.unibl.etf.projektnizadatak2024;

import org.unibl.etf.projektnizadatak2024.exceptions.IllegalCoordinatesException;
import org.unibl.etf.projektnizadatak2024.exceptions.MissingQuotationMarksException;
import org.unibl.etf.projektnizadatak2024.exceptions.ParsingNullElements;
import org.unibl.etf.projektnizadatak2024.user.DomesticUser;
import org.unibl.etf.projektnizadatak2024.user.ForeignUser;
import org.unibl.etf.projektnizadatak2024.user.User;
import org.unibl.etf.projektnizadatak2024.vehicle.Bike;
import org.unibl.etf.projektnizadatak2024.vehicle.Car;
import org.unibl.etf.projektnizadatak2024.vehicle.Scooter;
import org.unibl.etf.projektnizadatak2024.vehicle.Vehicle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ePJ2Company {

    public static List<Vehicle> vehicles;
    public static List<Rental> rentals;
    public static List<User> usersList = new ArrayList<>();
    public static int i = 0;

    public static List<Vehicle> parseVehicles(Path path) throws IOException, ParseException {
        vehicles = new ArrayList<>();
        List<String> lines = Files.readAllLines(path);
        for (String line : lines) {
            if (!line.contains("ID")) {
                try {
                    String[] parts = line.split(",");

                    String idCheck = parts[0];
                    boolean vehicleExists = vehicles.stream().anyMatch(vehicle -> vehicle.getId().equals(idCheck));

                    if (vehicleExists) {
                        System.out.println("Vehicle with ID " + idCheck + " already exists. Skipping.");
                        continue;
                    }
                    boolean notnull = checkIfNotNull(parts[8]);
                    if (notnull) {
                        if (Objects.equals("automobil", parts[8])) {
                            boolean notNull = checkIfNotNull(parts[0], parts[1], parts[2], parts[3], parts[4], parts[7]);
                            if (notNull) {
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy.");
                                Date date = simpleDateFormat.parse(parts[3]);
                                vehicles.add(new Car(parts[0], Double.parseDouble(parts[4]), parts[1], parts[2], 100, date, parts[7]));
                            } else throw new ParsingNullElements();
                        } else if (Objects.equals(parts[8], "bicikl")) {
                            boolean notNull = checkIfNotNull(parts[0], parts[1], parts[2], parts[4], parts[5]);
                            if (notNull) {
                                vehicles.add(new Bike(parts[0], Double.parseDouble(parts[4]), parts[1], parts[2], 100, Double.parseDouble(parts[5])));
                            } else throw new ParsingNullElements();
                        } else if (Objects.equals(parts[8], "trotinet")) {
                            boolean notNull = checkIfNotNull(parts[0], parts[1], parts[2], parts[4], parts[6]);
                            if (notNull) {
                                vehicles.add(new Scooter(parts[0], Double.parseDouble(parts[4]), parts[1], parts[2], 100, Double.parseDouble(parts[6])));
                            } else throw new ParsingNullElements();
                        }
                    } else {
                        System.out.println("Vehicle " + parts[8] + " not recognized. Skiping.");
                        throw new ParsingNullElements();
                    }
                } catch (ParsingNullElements e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return vehicles;
    }

    private static boolean checkIfNotNull(String... parts) {
        for (String part : parts) {
            if (part == null) {
                return false;
            }
        }
        return true;
    }

    public static Map<String, Vehicle> parseVehiclesToMap(Path path) throws IOException, ParseException {
        List<Vehicle> vehicles = parseVehicles(path);
        Map<String, Vehicle> vehicleMap = new HashMap<>();

        for (Vehicle vehicle : vehicles) {
            vehicleMap.put(vehicle.getId(), vehicle);
        }

        return vehicleMap;
    }

    public static void parseRentals(Path path) {
        try {
            rentals = new ArrayList<>();
            Set<String> seenRentals = new HashSet<>();
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                if (!line.contains("Datum")) {
                    String[] parts = line.split(",");
                    try {
                        boolean notnull = checkIfNotNull(parts[1], parts[2], parts[3], parts[4], parts[5], parts[6], parts[7], parts[8], parts[9]);
                        if (!notnull) {
                            throw new ParsingNullElements();
                        }
                        if (parts[3].startsWith("\"") && parts[4].endsWith("\"") && parts[5].startsWith("\"") && parts[6].endsWith("\"")) {
                            String pocetakKoordStrX = parts[3].replaceAll("\"", " ");
                            int xPocetno = Integer.parseInt(pocetakKoordStrX.trim());

                            String pocetakKoordStrY = parts[4].replaceAll("\"", " ");
                            int yPocetno = Integer.parseInt(pocetakKoordStrY.trim());

                            String krajKoordStrX = parts[5].replace("\"", " ");
                            int xKrajnje = Integer.parseInt(krajKoordStrX.trim());

                            String krajKoordStrY = parts[6].replace("\"", " ");
                            int yKrajnje = Integer.parseInt(krajKoordStrY.trim());

                            if (xPocetno < 0 || xPocetno > 19 || yPocetno < 0 || yPocetno > 19) {
                                try {
                                    throw new IllegalCoordinatesException();
                                } catch (IllegalCoordinatesException e) {
                                    System.out.println(e.getMessage());
                                }
                                continue;
                            }

                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy HH:mm");
                            LocalDateTime datumVrijeme = LocalDateTime.parse(parts[0], formatter);
                            String voziloId = parts[2];
                            String rentalKey = datumVrijeme + "_" + voziloId;

                            boolean vehicleExists = vehicles.stream().anyMatch(vehicle -> vehicle.getId().equals(voziloId));
                            if (vehicleExists) {
                                if (!seenRentals.contains(rentalKey)) {
                                    seenRentals.add(rentalKey);
                                    boolean jeImaoKvar = parts[8].equals("da");
                                    boolean imaPromocija = parts[9].equals("da");
                                    User user = getUser(voziloId, parts);
                                    usersList.add(user);
                                    rentals.add(new Rental(datumVrijeme, user, parts[2], xPocetno, yPocetno, xKrajnje, yKrajnje, Double.parseDouble(parts[7]), jeImaoKvar, imaPromocija));
                                } else {
                                    System.out.println("Duplicate rental for vehicle ID " + voziloId + " at " + datumVrijeme + " ignored.");
                                }
                            } else {
                                System.out.println("Rental for vehicle ID " + voziloId + " ignored because the vehicle does not exist.");
                            }
                        } else {
                            throw new MissingQuotationMarksException();
                        }
                    } catch (MissingQuotationMarksException e) {
                        System.out.println("Error occupied. Missing quotation marks while parsing coordinates in line: " + line);
                    } catch (ParsingNullElements e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error occupied while reading file: " + e.getMessage());
        }
    }

    private static User getUser(String voziloId, String[] parts) {
        i++;
        User user;
        if (voziloId.startsWith("A")) {
            if (i % 2 == 0) {
                user = new DomesticUser(parts[1], "VD" + i, "LK" + i);
            } else {
                user = new ForeignUser(parts[1], "VD" + i, "PS" + i);
            }
        } else {
            user = new User(parts[1]);
        }
        return user;
    }

    public static void sortRentalsByDateTime() {
        rentals.sort(Comparator.comparing(Rental::getRentalDateTime));
    }

    public static void makeEveryTenthRental() {
        for (int i = 10; i < rentals.size(); i = i + 10) {
            rentals.get(i).setTenth(true);
        }
    }

}
