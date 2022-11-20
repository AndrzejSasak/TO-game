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
  
