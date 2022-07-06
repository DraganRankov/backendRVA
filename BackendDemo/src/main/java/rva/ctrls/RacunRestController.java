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
import rva.jpa.Racun;
import rva.repository.RacunRepository;
@CrossOrigin
@Api(tags = ("Kredit CRUD operacije"))
@RestController
public class RacunRestController {
		private JdbcTemplate jdbcTemplate;
		
		@Autowired
		private RacunRepository RacunRepository;
		@ApiOperation(value="Vraća kolekciju svih računa iz baze podataka")
		@GetMapping("racun")
		public Collection<Racun> getRacun() {
			return RacunRepository.findAll();
		}
		
		@ApiOperation(value="Vraća konkretan račun na osnovu prosleđenog ID")
		@SuppressWarnings("deprecation")
		@GetMapping("racun/{id}")
		public Racun getRacun(@PathVariable("id") Integer id) {
			return RacunRepository.getOne(id);
		}
		
		@ApiOperation(value="Vraća kolekciju računa na osnovu prosleđenog naziva kredita")
		@GetMapping("RacunNacinPlacanja/{nacin_placanja}")
		public Collection<Racun> getRacunByNaziv(@PathVariable("nacin_placanja") String nacin_placanja) {
			return RacunRepository.findByNacinPlacanjaContainingIgnoreCase(nacin_placanja);
		}
		@ApiOperation(value="Dodaje novi račun u bazu podataka")
		@PostMapping("racun")
		public ResponseEntity<Racun> insertRacun(@RequestBody Racun racun) {
			if (!RacunRepository.existsById(racun.getId())) {
				RacunRepository.save(racun);
				return new ResponseEntity<Racun>(HttpStatus.OK);
			}
			return new ResponseEntity<Racun>(HttpStatus.CONFLICT);
		}
		@ApiOperation(value="Update-uje račun iz baze podataka")
		@PutMapping("racun")
		public ResponseEntity<Racun> updateRacun(@RequestBody Racun racun) {
			if(RacunRepository.existsById(racun.getId())) {
				RacunRepository.save(racun);
				return new ResponseEntity<Racun>(HttpStatus.OK);

			}
			return new ResponseEntity<Racun>(HttpStatus.NO_CONTENT);
		}
		@ApiOperation(value="Briše račun iz baze podataka na osnovu prosleđenog ID")
		@DeleteMapping("racun/{id}")
		public ResponseEntity<Racun> deleteRacun(@PathVariable("id") Integer id) {
			if(RacunRepository.existsById(id)) {
				RacunRepository.deleteById(id);

				if (id == -100)
				{
					jdbcTemplate.execute("INSERT INTO racun (id, naziv, adresa, kontakt) values (-100, 'Test naziv', 'test adresa', 'test kontakt')");
				}
				return new ResponseEntity<Racun>(HttpStatus.OK);
			}
			return new ResponseEntity<Racun>(HttpStatus.NO_CONTENT);
		}
}

