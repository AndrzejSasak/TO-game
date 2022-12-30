# TO-game

1. Temat projektu

Tematem projektu będzie gra umożliwiająca rozgrywkę przez sieć lokalną (singleplayer lub LAN multiplayer). Rozgrywka będzie polegała na walce dwóch przeciwników - w trybie turowym. Tryb singleplayer będzie posiadał kilka poziomów, a w trybie multiplayer gracze będą walczyć przeciwko sobie - do 3 wygranych rund.

2. Wzorce projektowe
- Wzorce kreacyjne:
  - Builder - do tworzenia instancji przeciwników w grze
  - Singleton - do stworzenia instancji serwera
  - Factory method - pozwalający na zmienianie typu tworzonych obiektów, dla różnych rodzajów przeciwników
- Wzorce strukturalne
  - Decorator - do dodawania specjalnych, dodatkowych funkcjonalności dla klas związanych z przeciwnikiem
  - Proxy - kontroluje dostęp do danych
- Wzorce czynnościowe:
  - Command - do obsługi poleceń w rozgrywce turowej
  - Iterator - pozwalający iterować po kolekcjach różnych rodzajów przeciwników
  - Template method - do nadpisywania poszczególnych etapów algorytmów walki, dla różnych rodzajów przeciwników
  
3. Uruchamianie
- wszystkie komendy zatwierdzamy enterem
- Po sklonowaniu uruchamiamy program po czym wpisujemy login gracza np. player
- Następnie wybieramy klase postaci poprzez wpisanie jej nazwy oraz po spacji wpisujemy nazwe bohatera np. archer arrow
- Uruchmia się menu po którym poruszamy się wpisując odpowiednie cyfry 
  - 1 . Tryb singleplayer wybieramy komendy poprzez wybranie przeciwnika do zatakowanie lub poczekanie i załadowanie ataku krytycznego
  - 2 . Tryb multiplayer (mozemy zostać hostem i czekamy na połaczenie gracza lub mozemy dołaczyć do gry poprzez wpisanie ip z dostepnych
  - 3 . Leaderboard wyswietlajacy ranking graczy
  - 4 . Zmiana klasy posaci i opcjonalnie nazwy
  - 5 . zakonczenie wykonywania programu
