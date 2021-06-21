package de.hft.softec.dbsys2.crimemap;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@SpringBootApplication
@Controller
public class CrimemapApplication {

	private Logger logger = LoggerFactory.getLogger(CrimemapApplication.class);

	@Autowired
	private CrimeRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(CrimemapApplication.class, args);
	}

	@GetMapping
	public String getCrimes(Model model) {

		List<Crime> crimes = repository.findAll();
		model.addAttribute("crimes", crimes);

		return "crimes";

	}

	@GetMapping("/edit-crime/{id}")
	public String editCrime(@PathVariable("id") long id, Model model) {
		model.addAttribute("crime", repository.findById(id));
		return "edit-crime";
	}

	@GetMapping("/edit-crime")
	public String editCrime(Model model) {
		model.addAttribute("crime", new Crime());
		return "edit-crime";
	}

	@PostMapping("/save-crime")
	public String saveCrime(@ModelAttribute Crime crime) {
		repository.save(crime);
		return "redirect:/";
	}

	// ------------------------------------------------------------------------
	@RequestMapping(value = "/crimes", method = RequestMethod.GET)
	public String listAllCrimes() {

		logger.info("--- listAllCrimes");

		List<Crime> crimes = repository.findAll();
		String geoJSONString = GeoJSON.convertToGeoJSON(crimes);

		return geoJSONString;

	}

	// // ------------------------------------------------------------------------
	// @RequestMapping(value="/crimes", method=RequestMethod.POST)
	// public String addCrime(@RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dateTime,
	// 				@RequestParam(required = true) int zip,
	// 				@RequestParam(required = true) String city,
	// 				@RequestParam(required = true) String address,
	// 				@RequestParam(required = true) BigDecimal lat,
	// 				@RequestParam(required = true) BigDecimal lon,
	// 				@RequestParam(required = true) String offense,
	// 				@RequestParam(required = false) String description,
	// 				@RequestParam(required = false) String urlToPolicePressRelease) {
		
	// 	Crime crime = new Crime(dateTime, zip, city, address, lat, lon, offense);
	// 	if (StringUtils.hasText(description)) {
	// 		crime.setDescription(description);
	// 	}
	// 	if (StringUtils.hasText(urlToPolicePressRelease)) {
	// 		crime.setUrlToPolicePressRelease(urlToPolicePressRelease);
	// 	}

	// 	repository.save(crime);
	// 	return "New crime added: " + crime.toString();

	// }

	// @Bean
	// public CommandLineRunner loadData(CrimeRepository repository) {

	// 	return (args) -> {

	// 		repository.save(new Crime());
	// 		repository.save(new Crime());
	// 		repository.save(new Crime());
	// 		repository.save(new Crime());
			
	// 	};

	// }
	

}
