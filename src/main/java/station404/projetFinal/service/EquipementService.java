package station404.projetFinal.service;

import org.springframework.stereotype.Service;
import station404.projetFinal.model.Equipement;
import station404.projetFinal.model.Incident;
import station404.projetFinal.repository.EquipementRepository;
import station404.projetFinal.repository.IncidentRepository;
import station404.projetFinal.exception.EquipementIntrouvableException;
import station404.projetFinal.model.Equipement;
import station404.projetFinal.repository.EquipementRepository;

import java.util.List;

@Service
public class EquipementService {

    private final EquipementRepository equipementRepository;
    public EquipementService(EquipementRepository equipementRepository) { this.equipementRepository = equipementRepository; }

    public List<Equipement> getEquipements() {
        return equipementRepository.findAll();
    }


}
    private final EquipementRepository equipementRepository;

    public EquipementService(EquipementRepository equipementRepository) {
        this.equipementRepository = equipementRepository;
    }

    public List<Equipement> listerEquipements(Integer stationId){
        return equipementRepository.findByStationId(stationId);
    }

    public Equipement chercherEquipement(Integer id){
        return equipementRepository.chercherAvecStation(id)
                .orElseThrow(() -> new EquipementIntrouvableException(id));
    }
}
