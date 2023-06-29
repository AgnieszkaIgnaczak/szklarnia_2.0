package com.szklarnia.service;

import com.szklarnia.exception_handler.ApiRequestException;
import com.szklarnia.model.Gardener;
import com.szklarnia.model.Greenhouse;
import com.szklarnia.repository.GardenerRepository;
import com.szklarnia.repository.GreenhouseRepository;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

//@Transactional - nad klasą testującą, niepotrzebne przy testach jednostkowych, na całej aplikacji, odwołanie do bazy, poleci na bazie jak wszystko się wykona (łączy wszystko w jedną transakcję)
//@SpringBootTest - do testów na więcej niż jednej warstwie, łączy z bazą danych
//testy jednostkowe — wewnętrzny program do sprawdzania logiki z serwisu, nie ma impaktu na bazie
class GardenerServiceTest {

    //adnotacje do dependency injection/wiring in Spring - @Autowired(= @Inject, tylko że jest ze Spring framework), @Resource, @Inject
    @Resource
    //tworzy instancję klasy i wstrzykuje mock'a utworzonego poprzez @Mock
    @InjectMocks
    private GardenerService gardenerService;

    //mock - udaje inne komponenty w kodzie, symuluje odpowiedź jakiegoś komponentu, tworzy mock'a
    @Mock
    private GardenerRepository gardenerRepository;

    @Mock
    private GreenhouseRepository greenhouseRepository;

    //adnotacja umożliwia odpalenie danej metody przed każdym testem
    //metoda ta musi być void, musi być public, nie może być static
    @BeforeEach
    public void beforeTest() {
        MockitoAnnotations.openMocks(this); //inicjalizuje mocki i je wstrzykuje
    }

    //adnotacja do testów jednostkowych
    //@ParameterizedTest - test parametryzowany, oczekuje danych wejściowych
    //Assertions is a collection of utility methods that support asserting conditions in tests(JUNIT).

    //POST

    @Test
    public void shouldSaveCorrectlyNewGardenerWithId() {

        Gardener gardener1 = new Gardener(9, "Zbigniew Lewkonia", 25, "ul.Chwastowa 70", 5500.0F); //lub bez id
        when(gardenerRepository.save(gardener1)).thenReturn(gardener1); //mockowanie, udaje metody z repo
        Gardener savedGardener1 = this.gardenerService.postNewGardener(gardener1); //realna metoda z serwisu
        assertEquals(savedGardener1, gardener1); //porównuje to, co zapisane na repo z tym, co zwraca metoda z serwisu, expected vs. actual
    }

    @Test
    public void shouldNotSaveNewGardenerWithExistingId() {

        ApiRequestException thrown = Assertions.assertThrows(ApiRequestException.class, () -> {
            Gardener gardener2 = new Gardener(8, "Zbigniew Złotokap", 2, "ul.Zalesiona 71", 5600.0F);
            when(gardenerRepository.existsById(gardener2.getGardenerId())).thenReturn(true); //druga część warunku z metody serwisowej zwraca ture, jeśli id istnieje
            this.gardenerService.postNewGardener(gardener2);
        });
        assertEquals("Cannot post gardener with existing ID.", thrown.getMessage()); //dodatkowe sprawdzenie, test wiadomości zwrotnej w przypadku wyrzucenia wyjątku, tu vs. tekst w metodzie serwisu
    }

    //GET ALL

    @Test
    public void shouldReturnCorrectlyAllGardeners() {
        List<Gardener> listOfGardeners = new ArrayList<>();
        listOfGardeners.add(new Gardener("Grzegorz Fiołek", 15, "ul.Działkowa 34", 4500F));
        listOfGardeners.add(new Gardener("Zbigniew Lewkonia", 25, "ul.Chwastowa 70", 5500F));
        listOfGardeners.add(new Gardener("Arkadiusz Szypułka", 15, "ul.Kwiecista 11", 7700F));

        when(gardenerRepository.findAll()).thenReturn(listOfGardeners); //mock
        Iterable<Gardener> result = gardenerService.getAllGardeners();
        assertEquals(listOfGardeners, result);
    }

    //DELETE BY ID

    @Test
    public void shouldDeleteGardenerByExistingId() {

        int gardenerId = 10;

        when(gardenerRepository.existsById(gardenerId)).thenReturn(true);
        gardenerService.deleteGardenerById(gardenerId);
        verify(gardenerRepository, times(1)).deleteById(gardenerId);
    }

    @Test
    public void shouldNotDeleteGardenerByNonExistingIdEmptyRepo() {
        ApiRequestException thrown = Assertions.assertThrows(ApiRequestException.class, () -> {
            int gardenerIdToDelete = 11;
            when(gardenerRepository.existsById(gardenerIdToDelete)).thenReturn(false); //oczekuję, że nie znajduje ID
            gardenerService.deleteGardenerById(gardenerIdToDelete); // oczekuję exception
        });
        assertEquals("Cannot delete gardener with non-existing ID.", thrown.getMessage());
    }

    //FIND BY ID

    @Test
    public void shouldFindGardenerByExistingId() {

        int gardenerId = 1;
        Gardener gardener = new Gardener(gardenerId, "Zbigniew Złotokap", 2, "ul.Zalesiona 71", 5600.0F);
        Optional<Gardener> optionalGardener = Optional.of(gardener);
        when(gardenerRepository.findById(gardenerId)).thenReturn(optionalGardener);

        Optional<Gardener> result = Optional.of(gardenerService.getGardenerById(gardenerId));
        assertEquals(optionalGardener, result);
    }

    @Test
    public void shouldNotFindGardenerByNonExistingId() {
        ApiRequestException thrown = Assertions.assertThrows(ApiRequestException.class, () -> {
            int gardenerToFindById = 11;
            when(gardenerRepository.findById(gardenerToFindById)).thenReturn(Optional.empty());
            gardenerService.getGardenerById(gardenerToFindById);
        });
        assertEquals("Cannot get gardener with non-existing ID.", thrown.getMessage());
    }

    @Test
    public void shouldFindGardenerByExistingName() {

        List<Gardener> listOfGardeners = new ArrayList<>();
        listOfGardeners.add(new Gardener("Grzegorz Fiołek", 15, "ul.Działkowa 34", 4500F));
        listOfGardeners.add(new Gardener("Grzegorz Lewkonia", 25, "ul.Chwastowa 70", 5500F));
        listOfGardeners.add(new Gardener("Grzegorz Szypułka", 15, "ul.Kwiecista 11", 7700F));

        when(gardenerRepository.findAllByNameContaining("Grzegorz")).thenReturn(listOfGardeners); //mock
        Iterable<Gardener> result = gardenerService.findAllGardenersByName("Grzegorz"); //test
        assertEquals(listOfGardeners, result); //porównanie wyników
    }

    @Test
    public void shouldUpdateGardenerByExistingId() {

        int gardenerId = 1;

        Gardener updatedGardener = new Gardener();
        updatedGardener.setGardenerId(gardenerId);
        updatedGardener.setExperience(30);

        //mock
        when(gardenerRepository.existsById(gardenerId)).thenReturn(true);
        when(gardenerRepository.save(updatedGardener)).thenReturn(updatedGardener);

        gardenerService.completeGardenerEntityUpdated(gardenerId, updatedGardener);
        verify(gardenerRepository, times(1)).save(updatedGardener);
    }

    @Test
    public void shouldNotUpdateGardenerByNonExistingId() {
        ApiRequestException thrown = Assertions.assertThrows(ApiRequestException.class, () -> {
            int gardenerId = 11;
            Gardener gardenerToBeUpdated = new Gardener();
            when(gardenerRepository.existsById(gardenerId)).thenReturn(false);
            gardenerService.completeGardenerEntityUpdated(gardenerId, gardenerToBeUpdated);
        });
        assertEquals("Cannot update gardener with provided ID.", thrown.getMessage());
    }

    @Test
    public void shouldSetGreenhouseForGardenerWhenBothExist() {
        int gardenerId = 1;
        int greenhouseId = 1;

        Gardener gardener = new Gardener(gardenerId, "Grzegorz Fiołek", 15, "ul.Działkowa 34", 4500F);
        Greenhouse greenhouse = new Greenhouse(greenhouseId, 60F, "glass", 200F, "robots", "hydroponic");

        Optional<Gardener> optionalGardener = Optional.of(gardener);
        Optional<Greenhouse> optionalGreenhouse = Optional.of(greenhouse);

        when(gardenerRepository.existsById(gardenerId)).thenReturn(true);
        when(greenhouseRepository.existsById(greenhouseId)).thenReturn(true);

        when(gardenerRepository.findById(gardenerId)).thenReturn(optionalGardener);
        when(greenhouseRepository.findById(greenhouseId)).thenReturn(optionalGreenhouse);

        gardener.setGreenhouse(greenhouse);

        when(gardenerRepository.save(gardener)).thenReturn(gardener);

        this.gardenerService.setGreenhouseForGardener(greenhouseId, gardenerId);

        verify(gardenerRepository, times(1)).save(gardener);

    }

    @Test
    public void shouldNotSetGreenhouseForGardenerWhenBothDoNotExist() {
        ApiRequestException thrown = Assertions.assertThrows(ApiRequestException.class, () -> {
            int gardenerId = 11;
            int greenhouseId = 11;
            when(gardenerRepository.existsById(gardenerId)).thenReturn(false);
            gardenerService.setGreenhouseForGardener(greenhouseId, gardenerId);
        });
        assertEquals("Cannot set greenhouse for gardener because gardener ID does not exist.", thrown.getMessage());
    }

    @Test
    public void shouldNotSetGreenhouseForGardenerWhenGreenhouseDoesNotExist() {
        ApiRequestException thrown = Assertions.assertThrows(ApiRequestException.class, () -> {
            int greenhouseId = 1;
            int gardenerId = 1;

            Gardener gardener = new Gardener();

            //mock, metoda po metodzie z metody service
            when(gardenerRepository.existsById(gardenerId)).thenReturn(true);
            when(gardenerRepository.findById(gardenerId)).thenReturn(Optional.of(gardener));
            when(greenhouseRepository.existsById(greenhouseId)).thenReturn(false);

            gardenerService.setGreenhouseForGardener(greenhouseId, gardenerId);
        });
        assertEquals("Cannot set greenhouse for gardener because greenhouse ID does not exist.", thrown.getMessage());
    }
}
