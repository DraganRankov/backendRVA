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
import rva.jpa.Proizvodjac;
import rva.repository.ProizvodjacRepository;
@CrossOrigin
@Api(tags = ("Klijent CRUD operacije"))
@RestController
public class ProizvodjacRestController {
		private JdbcTemplate jdbcTemplate;
		
		@Autowired
		private ProizvodjacRepository ProizvodjacRepository;
		@ApiOperation(value="Vraća kolekciju svih proizvođača iz baze podataka")
		@GetMapping("proizvodjac")
		public Collection<Proizvodjac> getProizvodjaci() {
			return ProizvodjacRepository.findAll();
		}
		
		@ApiOperation(value="Vraća konkretnog proizvođača na osnovu prosleđenog ID")
		@SuppressWarnings("deprecation")
		@GetMapping("proizvodjac/{id}")
		public Proizvodjac getProizvodjac(@PathVariable("id") Integer id) {
			return ProizvodjacRepository.getOne(id);
		}
		
		@ApiOperation(value="Vraća kolekciju proizvođača na osnovu prosleđenog imena proizvođača")
		@GetMapping("proizvodjacNaziv/{naziv}")
		public Collection<Proizvodjac> getProizvodjacByNaziv(@PathVariable("naziv") String naziv) {
			return ProizvodjacRepository.findByNazivContainingIgnoreCase(naziv);
		}
		@ApiOperation(value="Dodaje novog proizvođača u bazu podataka")
		@PostMapping("proizvodjac")
		public ResponseEntity<Proizvodjac> insertProizvodjac(@RequestBody Proizvodjac proizvodjac) {
			if (!ProizvodjacRepository.existsById(proizvodjac.getId())) {
				ProizvodjacRepository.save(proizvodjac);
				return new ResponseEntity<Proizvodjac>(HttpStatus.OK);
			}
			return new ResponseEntity<Proizvodjac>(HttpStatus.CONFLICT);
		}
		@ApiOperation(value="Update-uje proizvođača iz baze podataka")
		@PutMapping("proizvodjac")
		public ResponseEntity<Proizvodjac> updateProizvodjac(@RequestBody Proizvodjac proizvodjac) {
			if(ProizvodjacRepository.existsById(proizvodjac.getId())) {
				ProizvodjacRepository.save(proizvodjac);
				return new ResponseEntity<Proizvodjac>(HttpStatus.OK);

			}
			return new ResponseEntity<Proizvodjac>(HttpStatus.NO_CONTENT);
		}
		@ApiOperation(value="Briše proizvođača iz baze podataka na osnovu prosleđenog ID")
		@DeleteMapping("proizvodjac/{id}")
		public ResponseEntity<Proizvodjac> deleteProizvodjac(@PathVariable("id") Integer id) {
			if(ProizvodjacRepository.existsById(id)) {
				ProizvodjacRepository.deleteById(id);

				if (id == -100)
				{
					jdbcTemplate.execute("INSERT INTO proizvodjac (id, naziv, adresa, kontakt) values (-100, 'Test naziv', 'test adresa', 'test kontakt')");
				}
				return new ResponseEntity<Proizvodjac>(HttpStatus.OK);
			}
			return new ResponseEntity<Proizvodjac>(HttpStatus.NO_CONTENT);
		}
}

