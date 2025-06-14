package org.ticket.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ticket.entities.Train;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class TrainService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private List<Train> trainList;

    private static final String TRAINS_PATH = "ticketBooking/TicketBooking/src/main/java/org/ticket/localDb/trains.json";

    public TrainService() throws IOException {
        File trains = new File(TRAINS_PATH);
        trainList = objectMapper.readValue(trains, new TypeReference<List<Train>>() {});
    }

    public List<Train> searchTrains(String source, String destination) throws IOException {
        return trainList.stream()
                .filter(train -> vaildTrain(train, source, destination))
                .toList();
    }

    public boolean addTrain(Train train) throws IOException {
        if (trainList.stream().anyMatch(t -> t.getTrainId().equals(train.getTrainId()))) {
            return false; // Train with the same ID already exists
        }
        trainList.add(train);
        saveTrainsToFile();
        return true;
    }
    private void saveTrainsToFile() throws IOException {
        File trainsFile = new File(TRAINS_PATH);
        objectMapper.writeValue(trainsFile, trainList);
    }

    public boolean vaildTrain(Train train, String source, String destination) {
        List<String> stations =train.getStations();
        return stations.contains(source) && stations.contains(destination) &&
                stations.indexOf(source) < stations.indexOf(destination);
    }

}
