package rva.ctrls;

import java.util.Collection;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import rva.jpa.Proizvod;
import rva.jpa.Racun;
import rva.jpa.StavkaRacuna;
import rva.repository.StavkaRacunaRepository;
import rva.repository.ProizvodRepository;
import rva.repository.RacunRepository;
@CrossOrigin
@Api(tags = ("Kredit CRUD operacije"))
@RestController
public class StavkaRacunaController {
		private JdbcTemplate jdbcTemplate;
		
		@Autowired
		private StavkaRacunaRepository StavkaRacunaRepository;
		@Autowired
		private RacunRepository RacunRepository;
		@Autowired
		private ProizvodRepository ProizvodRepository;
		
		@ApiOperation(value="Vraća kolekciju svih stavki računa iz baze podataka")
		@GetMapping("stavkaRacuna")
		public Collection<StavkaRacuna> getRacun() {
			return StavkaRacunaRepository.findAll();
		}
		
		@ApiOperation(value="Vraća konkretnu stavku računa na osnovu prosleđenog ID")
		@SuppressWarnings("deprecation")
		@GetMapping("stavkaRacuna/{id}")
		public StavkaRacuna getStavkaRacuna(@PathVariable("id") Integer id) {
			return StavkaRacunaRepository.getOne(id);
		}
		
		@ApiOperation(value="Vraća kolekciju stavki računa na osnovu prosleđenog naziva jedinice mere")
		@GetMapping("stavkaRacunaJedinicaMere/{jedinica_mere}")
		public Collection<StavkaRacuna> getStavkaRacunaByJedinicaMere(@PathVariable("jedinica_mere") String jedinica_mere) {
			return StavkaRacunaRepository.findByJedinicaMereContainingIgnoreCase(jedinica_mere);
		}
		@ApiOperation(value="Vraća konkretnu stavku računa na osnovu prosleđenog IDja računa")
		@GetMapping("stavkaRacunaRacun/{id}")
		public Collection<StavkaRacuna> getStavkaRacunaByRacun(@PathVariable("id") Integer id) {
			Racun racun  = RacunRepository.getOne(id);
			return StavkaRacunaRepository.findByRacun(racun);
		}
		@ApiOperation(value="Vraća konkretnu stavku računa na osnovu prosleđenog IDja proizvoda")
		@GetMapping("stavkaRacunaProizvod/{id}")
			public Collection<StavkaRacuna> getStavkaRacunaByProizvod(@PathVariable("id") Integer id) {
				Proizvod proizvod  = ProizvodRepository.getOne(id);
				return StavkaRacunaRepository.findByProizvod(proizvod);
		}
		@ApiOperation(value="Dodaje novu stavku računa u bazu podataka")
		@PostMapping("stavkaRacuna")
		public ResponseEntity<StavkaRacuna> insertStavkaRacuna(@RequestBody StavkaRacuna stavkaRacuna) {
			if (!StavkaRacunaRepository.existsById(stavkaRacuna.getId())) {
				StavkaRacunaRepository.save(stavkaRacuna);
				return new ResponseEntity<StavkaRacuna>(HttpStatus.OK);
			}
			return new ResponseEntity<StavkaRacuna>(HttpStatus.CONFLICT);
		}
		@ApiOperation(value="Update-uje stavku računa iz baze podataka")
		@PutMapping("stavkaRacuna")
		public ResponseEntity<StavkaRacuna> updateRacun(@RequestBody StavkaRacuna stavkaRacuna) {
			if(StavkaRacunaRepository.existsById(stavkaRacuna.getId())) {
				StavkaRacunaRepository.save(stavkaRacuna);
				return new ResponseEntity<StavkaRacuna>(HttpStatus.OK);

			}
			return new ResponseEntity<StavkaRacuna>(HttpStatus.NO_CONTENT);
		}
		@ApiOperation(value="Briše stavku računa iz baze podataka na osnovu prosleđenog ID")
		@DeleteMapping("stavkaRacuna/{id}")
		public ResponseEntity<StavkaRacuna> deleteRacun(@PathVariable("id") Integer id) {
			if(StavkaRacunaRepository.existsById(id)) {
				StavkaRacunaRepository.deleteById(id);

				if (id == -100)
				{
					jdbcTemplate.execute("INSERT INTO stavka_racuna (id, redni_broj, kolicina, jedinica_mere, cena, racun, proizvod) values (-100, 1, 1, 'test kontakt',1,1,1)");
				}
				return new ResponseEntity<StavkaRacuna>(HttpStatus.OK);
			}
			return new ResponseEntity<StavkaRacuna>(HttpStatus.NO_CONTENT);
		}
}

