package com.szklarnia.service;

import com.szklarnia.exception_handler.ApiRequestException;
import com.szklarnia.model.Gardener;
import com.szklarnia.repository.GardenerRepository;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

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
        Assertions.assertEquals(savedGardener1, gardener1); //porównuje to, co zapisane na repo z tym, co zwraca metoda z serwisu, expected vs. actual
    }

    @Test
    public void shouldNotSaveNewGardenerWithExistingId() {

        ApiRequestException thrown = Assertions.assertThrows(ApiRequestException.class, () -> {
            Gardener gardener2 = new Gardener(8, "Zbigniew Złotokap", 2, "ul.Zalesiona 71", 5600.0F);
            when(gardenerRepository.existsById(gardener2.getGardenerId())).thenReturn(true); //druga część warunku z metody serwisowej zwraca ture, jeśli id istnieje
            this.gardenerService.postNewGardener(gardener2);
        });
        Assertions.assertEquals("Cannot post gardener with existing ID.", thrown.getMessage()); //dodatkowe sprawdzenie, test wiadomości zwrotnej w przypadku wyrzucenia wyjątku, tu vs. tekst w metodzie serwisu
    }

    //GET ALL

    @Test
    public void shouldReturnCorrectlyAllGardeners() {
        List<Gardener> listOfGardeners = new ArrayList<>();
        listOfGardeners.add(new Gardener("Grzegorz Fiołek", 15, "ul.Działkowa 34", 4500F));
        listOfGardeners.add(new Gardener("Zbigniew Lewkonia", 25, "ul.Chwastowa 70", 5500F));
        listOfGardeners.add(new Gardener("Arkadiusz Szypułka", 15, "ul.Kwiecista 11", 7700F));

        when(gardenerRepository.findAll()).thenReturn(listOfGardeners);
        Iterable<Gardener> result = gardenerService.getAllGardeners();
        Assertions.assertEquals(listOfGardeners, result);
    }

    //DELETE

    @Test
    public void shouldDeleteGardenerByExistingId() {

    }

    @Test
    public void shouldNotDeleteGardenerByExistingId() {

    }


//        @Test
//        public void shouldFindCorrectlyGardenerById() {}
//        @Test
//        public void shouldFindCorrectlyExistingGardenerByName() {}
//
//        @Test
//        public void shouldUpdateCorrectlyExistingGardener() {}
//
//        @Test
//        public void shouldSetCorrectlyGreenhouseForGardener() {}

}