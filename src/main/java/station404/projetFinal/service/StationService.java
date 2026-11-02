package station404.projetFinal.service;

import org.springframework.stereotype.Service;
import station404.projetFinal.exception.StationIntrouvableException;
import station404.projetFinal.model.Station;
import station404.projetFinal.repository.StationRepository;

import java.util.List;

@Service
public class StationService {
    private final StationRepository stationRepository;
    public StationService(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    public List<Station> listerStations(){
        return stationRepository.findAllByOrderByNom();
    }

    public Station chercherStation(Integer id){
        return stationRepository.findById(id)
        .orElseThrow(() -> new StationIntrouvableException(id));
    }
}
