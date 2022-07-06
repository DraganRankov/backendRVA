package rva.repository;

import java.util.Collection;



import org.springframework.data.jpa.repository.JpaRepository;


import rva.jpa.StavkaRacuna;
import rva.jpa.Proizvod;
import rva.jpa.Racun;

public interface StavkaRacunaRepository extends JpaRepository<StavkaRacuna, Integer> {
	Collection<StavkaRacuna> findByJedinicaMereContainingIgnoreCase(String jedinicaMere);
	Collection<StavkaRacuna> findByRacun(Racun racun);
	Collection<StavkaRacuna> findByProizvod(Proizvod proizvod);
}
