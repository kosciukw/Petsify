# Petsify MVP Product Plan

## Cel MVP

MVP ma pokazać dwie rzeczy jednocześnie:

1. Projekt może działać jako sensowny szablon architektoniczny dla kolejnych aplikacji.
2. Projekt realizuje realną logikę biznesową dla aplikacji do zarządzania zwierzakami.

Pierwsza wersja produktu ma skupiać się na codziennej opiece nad zwierzakiem: profil zwierzaka, podstawowe dane, historia wagi, przypomnienia i prosty dashboard.

## Założenia MVP

- aplikacja ma mieć czytelny, atrakcyjny wizualnie interfejs
- aplikacja ma prowadzić użytkownika przez najważniejsze codzienne akcje
- MVP ma mieć pełny przepływ danych: UI -> domena -> API -> lokalna persystencja
- zakres powinien być mały, ale spójny end-to-end
- paywall i płatności nie są częścią pierwszego etapu implementacji

## Główne ekrany MVP

### 1. Home

Ekran główny powinien być dashboardem złożonym z kafelków, a nie zwykłą listą.

Cel ekranu:
- pokazać stan dnia
- pokazać najważniejsze dane o zwierzaku
- dać szybkie wejście do najczęściej używanych akcji

Sekcje / kafelki:
- aktywny zwierzak albo przełącznik między zwierzakami
- dzisiejsze zadania
- najbliższe przypomnienia
- ostatni wpis wagi
- ostatnia aktywność
- szybkie akcje

Szybkie akcje:
- dodaj zwierzaka
- dodaj wagę
- dodaj reminder
- przejdź do profilu zwierzaka

### 2. Pet Details

To jest centralny ekran domenowy aplikacji.

Górna sekcja:
- zdjęcie zwierzaka
- imię
- gatunek
- rasa
- wiek albo data urodzenia
- aktualna waga

Sekcje nawigacyjne:
- waga
- historia
- remindery
- plan dnia
- dane podstawowe

Ten ekran ma być hubem prowadzącym do kolejnych ścieżek.

### 3. Add / Edit Pet

Formularz tworzenia i edycji zwierzaka.

Zakres danych:
- imię
- gatunek
- rasa
- płeć
- data urodzenia albo wiek
- waga początkowa
- zdjęcie
- krótka notatka

### Biznesowy zakres ekranu Add Pet

Produkt ma być lekki i przyjazny, więc ekran `Add Pet` nie powinien wyglądać jak formalna kartoteka weterynaryjna.

Zasada:
- zbieramy tylko dane, które użytkownik zna od razu
- zbieramy tylko dane, które będą później realnie używane w aplikacji
- unikamy pól administracyjnych i technicznych na starcie

#### Pola obowiązkowe

- imię
- gatunek
- data urodzenia albo wiek
- aktualna waga

#### Pola opcjonalne

- zdjęcie zwierzaka
- rasa
- płeć
- temperament
- kolor albo umaszczenie
- krótka notatka

#### Pola niewchodzące do lekkiego MVP

- chipId
- dane formalne i administracyjne
- rozbudowane dane medyczne
- szczegóły weterynaryjne

#### Dlaczego temperament ma sens

`Temperament` jest sensownym polem opcjonalnym, ponieważ wspiera bardziej osobisty i przyjazny profil zwierzaka.

Możliwe przykładowe wartości:
- spokojny
- energiczny
- towarzyski
- lękliwy
- uparty

To pole nie jest krytyczne dla działania aplikacji, ale dobrze wspiera charakter produktu i może być wykorzystane później w profilu, komunikatach i personalizacji UI.

#### Dlaczego chipId nie jest potrzebny

`chipId` nie wspiera głównego celu MVP, którym jest codzienna opieka nad zwierzakiem.

To pole ma większy sens w aplikacji bardziej formalnej, nastawionej na:
- dokumentację
- wizyty weterynaryjne
- eksport danych
- identyfikację zwierzaka

W lekkim MVP lepiej je pominąć.

#### Rekomendacja UX

Ekran `Add Pet` powinien mieć niski próg wejścia.

Najlepiej:
- najpierw pokazać pola podstawowe
- pola opcjonalne trzymać niżej albo w sekcji `More details`

To zwiększa szansę, że użytkownik szybko doda pierwszego pupila i zacznie używać aplikacji.

#### Hierarchia ekranu

Ekran `Add Pet` powinien być podzielony na dwie wyraźne warstwy.

##### Górna warstwa: recommended / high-priority

To jest część widoczna od razu i służy do szybkiego dodania zwierzaka.

Pola:
- zdjęcie zwierzaka opcjonalnie, ale wysoko w układzie
- imię
- gatunek
- wiek albo data urodzenia
- aktualna waga

Ta sekcja ma działać jako szybkie dodanie zwierzaka.

##### Dolna warstwa: optional / more details

To jest część uzupełniająca, którą użytkownik może wypełnić później.

Pola:
- rasa
- płeć
- temperament
- kolor albo umaszczenie
- notatka

Ta sekcja powinna być umieszczona niżej albo pod rozwijanym `More details`.

#### Zasada zapisu

Dodanie zwierzaka powinno być możliwe już po uzupełnieniu danych z górnej warstwy.

Minimalny zestaw do zapisu:
- imię
- gatunek
- wiek albo data urodzenia
- waga

Zdjęcie pozostaje opcjonalne, mimo że jest wysoko w hierarchii wizualnej.

### 4. Settings

Zakres MVP:
- konto użytkownika
- ustawienia przypomnień
- jednostki wagi
- wygląd aplikacji
- sekcja placeholder pod przyszły model subskrypcji

## Pod-ekrany MVP

### Weight History

Zakres:
- lista wpisów wagi
- dodanie nowego wpisu
- data pomiaru
- notatka do pomiaru
- prosty wykres zmian wagi

### Reminders

Zakres:
- lista reminderów dla zwierzaka
- tworzenie reminderów
- typy reminderów:
  - spacer
  - karmienie
  - lek
  - własny
- częstotliwość:
  - jednorazowo
  - codziennie
  - wybrane dni tygodnia

### Care History

Zakres:
- lista ostatnich aktywności
- wpisy wykonanych działań
- oś czasu działań

Przykłady aktywności:
- spacer wykonany
- karmienie wykonane
- dodano wagę
- reminder oznaczony jako zrobiony

### Calendar

Opcjonalny ekran w MVP.

Jeśli wejdzie do pierwszej wersji:
- widok dnia
- lista zaplanowanych działań
- wejście do reminderów

Jeśli nie wejdzie:
- jego funkcję przejmują kafelki na Home i sekcja reminders

## Główne scenariusze użytkownika

### Scenariusz 1. Dodanie pierwszego zwierzaka

1. Użytkownik uruchamia aplikację.
2. Przechodzi do formularza dodania zwierzaka.
3. Uzupełnia podstawowe dane.
4. Po zapisaniu trafia na Home albo Pet Details.

### Scenariusz 2. Dodanie wpisu wagi

1. Użytkownik otwiera Pet Details.
2. Wchodzi do sekcji Weight History.
3. Dodaje nowy pomiar.
4. System aktualizuje ostatnią wagę i historię.
5. Home pokazuje najnowszy wpis.

### Scenariusz 3. Dodanie remindera

1. Użytkownik otwiera Pet Details albo Home.
2. Wybiera dodanie remindera.
3. Określa typ, godzinę i częstotliwość.
4. Reminder pojawia się na liście i na Home.

### Scenariusz 4. Oznaczenie zadania jako wykonane

1. Użytkownik na Home widzi dzisiejsze zadania.
2. Oznacza spacer lub karmienie jako wykonane.
3. System zapisuje wpis w historii aktywności.

## Zakres danych domenowych MVP

### Pet

Podstawowe pola:
- id
- name
- species
- breed
- sex
- birthDate
- currentWeight
- photoUrl albo localPhotoPath
- note

### Weight Entry

Podstawowe pola:
- id
- petId
- value
- unit
- measuredAt
- note

### Reminder

Podstawowe pola:
- id
- petId
- type
- title
- description
- scheduledTime
- recurrence
- isEnabled

### Care Activity

Podstawowe pola:
- id
- petId
- type
- createdAt
- source
- note

## Priorytety implementacyjne

### Etap 1

- Add / Edit Pet
- Pet Details
- Home z podstawowymi kafelkami

### Etap 2

- Weight History
- dodawanie wpisów wagi
- aktualizacja danych na Home i Pet Details

### Etap 3

- Reminders
- oznaczanie zadań jako wykonane
- Care History

### Etap 4

- Calendar
- dopracowanie UI
- przygotowanie miejsca pod subskrypcję

## Proponowany podział na feature'y

- `feature:home`
- `feature:pets`
- `feature:petdetails`
- `feature:weight`
- `feature:reminders`
- `feature:carehistory`
- `feature:settings`

## Co nie wchodzi do MVP

- pełny moduł płatności
- paywall produkcyjny
- współdzielenie zwierzaka z innymi użytkownikami
- dokumentacja medyczna
- rozbudowane statystyki
- synchronizacja multi-device w pełnym zakresie

## Kierunek po MVP

Po dowiezieniu MVP kolejnym etapem powinno być:
- domknięcie pełnej wymiany danych z API
- dopięcie lokalnej persystencji i synchronizacji
- wprowadzenie modelu premium
- wydzielenie mechanizmu paywalla jako modułu wielokrotnego użytku w przyszłych projektach
