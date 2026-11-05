package station404.projetFinal.service;

import org.springframework.stereotype.Service;
import station404.projetFinal.model.Prestataire;
import station404.projetFinal.repository.PrestataireRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PrestataireService {

    private final PrestataireRepository prestataireRepository;
    public PrestataireService(PrestataireRepository prestataireRepository) {
        this.prestataireRepository = prestataireRepository;
    }

    public List<Prestataire> getAllPrestataires() {
        return prestataireRepository.findAll();
    }

}
