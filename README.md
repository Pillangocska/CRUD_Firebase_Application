# Házi feladat specifikáció

## Bemutatás

Nemrég felkértek a közeli baráti társaságomból, hogy csináljak nekik egy appot, amiben az előfizetőikhez napi tippeket tudnának eljuttatni.(Nem támogatom a szerencsejátékot de nagy házinak tökéletes.) Egy olyan alkalmazás lesz ez, amiben lehet regisztrálni, és meglehet tekinteni a napi tippeket, akinek pedig olyan jogai vannak fel is tud tölteni rekordokat az adatbázisba.  

## Főbb funkciók

Az alkalmazás egy email és jelszó párossal fogja beengedni a felhasználót, illetve adatbázisban fogja tárolni a felhasználó adatait, és a tippeket. Az alkalmazás egy Firebase adatbázishoz fog csatlakozni. Az adatbázisban 2 szerep lesz: sima felhasználó (ők csak megtekinteni tudják a rekordokat ha beléptek a felhasználónév - jelszó párosukkal), és admin (ők új rekordokat tudnak felvenni, illetve tudják szerkeszteni is a meglévőket). Az appnak lesz valamilyen stílusa/témája, amit a legtöbb komponens használni fog, és természetesen UI-al fog rendelkezni az alkalmazás. Az egyes tippek egy RecyclerViewban fognak megjelenni.

## Választott technológiák:

- UI
- Fragmentek
- RecyclerView
- Perzisztens adattárolás
- Firebase Android