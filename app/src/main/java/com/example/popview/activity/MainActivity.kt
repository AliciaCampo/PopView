package com.example.popview.activity

import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.popview.R
import com.example.popview.data.Titulo
import com.example.popview.service.PopViewAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencia al ProgressBar
        progressBar = findViewById(R.id.progressBar)

        // Llamada a la API y se mide el tiempo
        lifecycleScope.launch {
            val tiempoCarga = verificarYCrearTitulosIniciales()
            // Ajuste de la barra de progreso
            cargarBarraProgreso(tiempoCarga)
        }
    }

    private suspend fun verificarYCrearTitulosIniciales(): Long {
        return withContext(Dispatchers.IO) {
            measureTimeMillis {
                try {
                    val api = PopViewAPI().API()
                    val titulosExistentes = api.getAllTitols()
                    val titulosIniciales = listOf(
                        Titulo(
                            imagen = "sabrina.jpg",
                            nombre = "Sabrina",
                            description = "Chilling Adventures of Sabrina és una nova versió de Sabrina, cosas de brujas i de la sèrie de còmics que rep el mateix nom que aquesta renovada producció de Warner Bros, la qual ha col·laborat juntament amb Berlanti Productions i Archie Comics",
                            genero = "Fantasía",
                            edadRecomendada = 12,
                            platforms = listOf("Netflix", "Amazon Prime"),
                            rating = 3.5f
                        ),
                        Titulo(
                            imagen = "strangerthingscuatro.jpg",
                            nombre = "Stranger Things",
                            description = "Stranger Things és una sèrie estatunidenca de ciència-ficció creada per Netflix escrita i dirigida pels germans Matt i Ross Duffer, amb Shawn Levy com a productor executiu.",
                            genero = "Ciencia ficción",
                            edadRecomendada = 16,
                            platforms = listOf("Netflix"),
                            rating = 4.0f
                        ),
                        Titulo(
                            imagen = "orange_is_the_new_black.jpg",
                            nombre = "Orange is the New Black",
                            description = "Orange is the new Black és una sèrie nord-americana de comèdia-drama, creada per Jenji Kohan. La sèrie fou produïda per Lionsgate Television i es va emetre per primera vegada a Netflix l'11 de juliol de 2013. Es basa en el llibre autobiogràfic de Piper Kerman, que relata les seves memòries sobre la vida a la presó.",
                            genero = "Drama",
                            edadRecomendada = 18,
                            platforms = listOf("Netflix"),
                            rating = 4.0f
                        ),
                        Titulo(
                            imagen = "wednesdaymiercoles.webp",
                            nombre = "Miércoles",
                            description = "La sèrie es tracta d'una comèdia de misteri sobrenatural i se centra en Dimecres Addams en els seus anys com a estudiant de secundària a l'Acadèmia Mai Més, on intenta dominar els seus poders psíquics, i aturar una monstruosa matança dels ciutadans de la ciutat, a més de resoldre el misteri sobrenatural que va afectar la seva família fa vint-i-cinc anys, tot mentre lidia amb les seves noves relacions personals",
                            genero = "Comedia",
                            edadRecomendada = 12,
                            platforms = listOf("Netflix"),
                            rating = 4.0f
                        ),
                        Titulo(
                            imagen = "DeadPool1.jpg",
                            nombre = "Deadpool",
                            description = "Un antic mercenari es converteix en un antiheroi amb habilitats regeneratives i molt sentit de l'humor.",
                            genero = "Acció",
                            edadRecomendada = 18,
                            platforms = listOf("Disney+", "Amazon Prime"),
                            rating = 4.5f
                        ),
                        Titulo(
                            imagen = "Deadpool2.jpg",
                            nombre = "Deadpool 2",
                            description = "Deadpool torna amb més acció, més humor i un equip de mutants per lluitar contra un enemic formidable.",
                            genero = "Acció",
                            edadRecomendada = 18,
                            platforms = listOf("Disney+", "Amazon Prime"),
                            rating = 4.3f
                        ),
                        Titulo(
                            imagen = "deadpoolylobezno.jpg",
                            nombre = "Deadpool y Wolverine",
                            description = "Una col·lisió explosiva entre dos dels personatges més irreverents de l'univers Marvel. Deadpool, amb la seva natura caòtica i humorística, s'uneix a Wolverine en una aventura plena d'acció, combat i grans dosis de sarcasme. Junts hauran d'enfrontar-se a nous enemics mentre es desafien mútuament.",
                            genero = "Acción",
                            edadRecomendada = 18,
                            platforms = listOf("Netflix"),
                            rating = 4.0f
                        ), Titulo(
                            imagen = "del_reves_2.jpg",
                            nombre = "Del Revés 2",
                            description = "Del revés 2 és una pel·lícula d'animació estatunidenca sobre la majoria d'edat produïda per Pixar Animation Studios per a Walt Disney Pictures. És la seqüela de Del revés, i és dirigida per Kelsey Mann, produïda per Mark Nielsen i escrita per Meg LeFauve. ",
                            genero = "Animación, Superhéroes",
                            edadRecomendada = 6,
                            platforms = listOf("Disney"),
                            rating = 4.0f
                        ),
                        Titulo(
                            imagen = "respira.jpg",
                            nombre = "Respira",
                            description = "Una sèrie tranquil·la i reflexiva dissenyada per ajudar els espectadors a relaxar-se i meditar. Amb visuals calmants i tècniques de respiració guiades, *Respira* ofereix una experiència de relaxació per a aquells que busquen moments de pau i desconnexió.",
                            genero = "Drama, Salud",
                            edadRecomendada = 16,
                            platforms = listOf("Netflix"),
                            rating = 4.0f
                        ),
                        Titulo(
                            imagen = "beetlejuice2.jpg",
                            nombre = "Beetlejuice 2",
                            description = "La continuació de la mítica comèdia de terror, *Beetlejuice 2* porta als espectadors novament al món excèntric i grotesc de Beetlejuice. La pel·lícula manté el to irreverent i l'humor negre mentre presenta nous personatges i situacions inesperades amb l'absurd com a protagonista.",
                            genero = "Comedia, Terror",
                            edadRecomendada = 12,
                            platforms = listOf("Netflix"),
                            rating = 4.0f
                        ),
                        Titulo(
                            imagen = "jokerdos.webp",
                            nombre = "Joker 2",
                            description = "La seqüela de la història de l'origen de Joker, que continua explorant les profunditats de la psicologia del personatge. Aquesta pel·lícula se centra en les conseqüències de la seva caiguda a la bogeria i la creació del seu alter ego com a figura de terror a la societat.",
                            genero = "Drama, Thriller",
                            edadRecomendada = 18,
                            platforms = listOf("Netflix"),
                            rating = 4.0f
                        ),
                        Titulo(
                            imagen = "juegocalamar.jpg",
                            nombre = "Joc del Calamar",
                            description = "Un grup de persones desesperades i endarrerides econòmicament són convidades a participar en un joc mortal per guanyar una fortuna. Els concursants hauran de competir en versions letals de jocs infantils mentre intenten sobreviure i descobrir qui està darrere d'aquesta macabra competició.",
                            genero = "Acción, Thriller",
                            edadRecomendada = 18,
                            platforms = listOf("Netflix"),
                            rating = 4.0f
                        ),
                        Titulo(
                            imagen = "robotsalvaje.webp",
                            nombre = "Robot Salvatge",
                            description = "En un futur pròxim, un robot creat per a la guerra es torna independent i s'escapa del control de les seves creadores. Amb una consciència pròpia, aquest robot lluita per entendre la seva pròpia identitat mentre s'enfronta als humans que volen desactivar-lo.",
                            genero = "Ciencia ficción, Acción",
                            edadRecomendada = 12,
                            platforms = listOf("Netflix"),
                            rating = 4.0f
                        ),
                        Titulo(
                            imagen = "venomuno.jpg",
                            nombre = "Venom",
                            description = "Eddie Brock es fusiona amb un simbiont alienígena i es converteix en Venom.",
                            genero = "Acció",
                            edadRecomendada = 14,
                            platforms = listOf("Amazon Prime", "HBO Max"),
                            rating = 2.5f
                        ),
                        Titulo(
                            imagen = "venomdos.jpg",
                            nombre = "Venom 2",
                            description = "Venom s'enfronta a Carnage en aquesta intensa seqüela.",
                            genero = "Acció",
                            edadRecomendada = 14,
                            platforms = listOf("Amazon Prime", "HBO Max"),
                            rating = 2.8f
                        ),
                        Titulo(
                            imagen = "venomtres.jpg",
                            nombre = "Venom 3",
                            description = "L'antic periodista Eddie Brock torna a unir-se amb el simbiòtic Venom per enfrontar-se a noves amenaces sobrenaturals. Junts, lluiten contra vilans poderosos, mentre exploren els límits de la seva relació amb els seus poders i la seva moralitat en un món cada vegada més perillós.",
                            genero = "Acción, Superhéroes",
                            edadRecomendada = 16,
                            platforms = listOf("Netflix"),
                            rating = 4.0f
                        ),
                        Titulo(
                            imagen = "cobrakai.jpg",
                            nombre = "Cobra Kai",
                            description = "El retorn de les tensions entre Johnny Lawrence i Daniel LaRusso. *Cobra Kai* segueix la història de Johnny, que intenta redimir-se mentre entrena una nova generació de karateques, mentre LaRusso lluita per mantenir els valors de la seva infància. Un conflicte entre generacions de karate a la recerca de venjança, perdó i redempció.",
                            genero = "Acción, Drama",
                            edadRecomendada = 12,
                            platforms = listOf("Netflix"),
                            rating = 4.0f
                        ),
                        Titulo(
                            imagen = "casapapel.jpg",
                            nombre = "La Casa de Papel",
                            description = "Un grup de lladres, sota la direcció del misteriós 'El Professor', s'infiltren a la Reial Casa de la Moneda d'Espanya per dur a terme el robatori més gran de la història. Tensions, estratègies, traïcions i emoció en una trama ple de suspens mentre els membres de la banda intenten mantenir-se un pas davant de la policia.",
                            genero = "Acción, Suspens",
                            edadRecomendada = 16,
                            platforms = listOf("Netflix"),
                            rating = 4.0f
                        ),
                        Titulo(
                            imagen = "the_walking_dead.jpg",
                            nombre = "The Walking Dead",
                            description = "En un món postapocalíptic devastat per una invasió zombi, un grup de supervivents lluita per la seva vida mentre busquen un lloc segur. La sèrie explora les relacions humanes, la moralitat en un món sense llei i la constant lluita per sobreviure en un paisatge ple de perills mortals.",
                            genero = "Terror, Drama",
                            edadRecomendada = 16,
                            platforms = listOf("Netflix"),
                            rating = 4.0f
                        ),
                        Titulo (
                            imagen = "StarWarsNewHope.jpg",
                            nombre = "Star Wars: Una nova esperança",
                            description = "Primera pel·lícula de Star Wars, on Luke Skywalker descobreix el seu destí.",
                            genero = "Ciència ficció",
                            edadRecomendada = 12,
                            platforms = listOf("Disney+"),
                            rating = 4.0f
                        ),
                        Titulo(
                            imagen = "StarWarsImperioContrataca.jpg",
                            nombre = "Star Wars: L'Imperi contraataca",
                            description = "L'Imperi liderat per Darth Vader persegueix la resistència en una èpica batalla.",
                            genero = "Ciència ficció",
                            edadRecomendada = 12,
                            platforms = listOf("Disney+"),
                            rating = 4.0f
                        ),
                        Titulo(
                            imagen = "StarWarsReturnJedi.webp",
                            nombre = "Star Wars: El retorn del Jedi",
                            description = "Luke Skywalker s'enfronta a Darth Vader i l'Emperador en una batalla final.",
                            genero = "Ciència ficció",
                            edadRecomendada = 12,
                            platforms = listOf("Disney+"),
                            rating = 3.5f
                        ),
                        Titulo(
                            imagen = "StarWarsPhantom.jpg",
                            nombre = "Star Wars: La amenaça fantasma",
                            description = "Els Jedi descobreixen un nen amb un gran potencial en la Força.",
                            genero = "Ciència ficció",
                            edadRecomendada = 12,
                            platforms = listOf("Disney+"),
                            rating = 3.2f
                        ),
                        Titulo(
                            imagen = "StarWarsAttackClones.jpg",
                            nombre = "Star Wars: L'atac dels clons",
                            description = "La República està en guerra i Anakin Skywalker s'enamora de Padmé Amidala.",
                            genero = "Ciència ficció",
                            edadRecomendada = 12,
                            platforms = listOf("Disney+"),
                            rating = 3.5f
                        ),
                        Titulo(
                            imagen = "StarWarsRevengeSith.jpg",
                            nombre = "Star Wars: La venjança dels Sith",
                            description = "Anakin cau en el costat fosc i es converteix en Darth Vader.",
                            genero = "Ciència ficció",
                            edadRecomendada = 12,
                            platforms = listOf("Disney+"),
                            rating = 3.5f
                        ),
                        Titulo(
                            imagen = "StarWarsForceAwakens.jpg",
                            nombre = "Star Wars: El despertar de la força",
                            description = "Una nova generació de herois s'enfronta a l'amenaça del Primer Ordre.",
                            genero = "Ciència ficció",
                            edadRecomendada = 12,
                            platforms = listOf("Disney+"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "StarWarsLastJedi.jpg",
                            nombre = "Star Wars: Els últims Jedi",
                            description = "Rey busca entrenament amb Luke Skywalker mentre la Resistència lluita per sobreviure.",
                            genero = "Ciència ficció",
                            edadRecomendada = 12,
                            platforms = listOf("Disney+"),
                            rating = 4.0f
                        ),
                        Titulo(
                            imagen = "StarWarsRiseSkyWalker.jpg",
                            nombre = "Star Wars: L'ascens de Skywalker",
                            description = "La batalla final entre la Resistència i el Primer Ordre decideix el destí de la galàxia.",
                            genero = "Ciència ficció",
                            edadRecomendada = 12,
                            platforms = listOf("Disney+"),
                            rating = 4.0f
                        ),
                        Titulo(
                            imagen = "spiderman_uno.jpg",
                            nombre = "Spider-Man",
                            description = "La primera pel·lícula de Spider-Man dirigida per Sam Raimi. Explica la història d'origen de Peter Parker, un estudiant que després de ser picat per una aranya radioactiva adquireix habilitats sobrehumanes i decideix utilitzar-les per combatre el crim a Nova York.",
                            genero = "Acció",
                            edadRecomendada = 12,
                            platforms = listOf("Amazon Prime"),
                            rating = 3.3f
                        ),
                        Titulo(
                            imagen = "spiderman_dos.jpg",
                            nombre = "Spider-Man 2",
                            description = "Peter Parker s'enfronta a Doctor Octopus en aquesta seqüela plena d'acció i emoció. Mentre intenta equilibrar la seva vida personal i el seu deure com a superheroi, es troba amb un nou enemic formidable que posa a prova els seus límits.",
                            genero = "Acció",
                            edadRecomendada = 12,
                            platforms = listOf("Amazon Prime"),
                            rating = 3.5f
                        ),
                        Titulo(
                            imagen = "spiderman_tres.jpg",
                            nombre = "Spider-Man 3",
                            description = "Peter Parker ha d'enfrontar-se a múltiples desafiaments, incloent els poderosos Venom i Sandman, mentre lluita contra el seu propi costat fosc a causa de l'influència d'un simbiont alienígena. Una entrega plena de drama i acció.",
                            genero = "Acció",
                            edadRecomendada = 13,
                            platforms = listOf("Amazon Prime"),
                            rating = 2.8f
                        ),
                        Titulo(
                            imagen = "tasm_uno.jpg",
                            nombre = "The Amazing Spider-Man",
                            description = "Un nou començament per a l'heroi aràcnid amb Andrew Garfield en el paper de Peter Parker. En aquesta versió, Peter investiga el passat dels seus pares i descobreix els secrets que el portaran a enfrontar-se al temible Llangardaix.",
                            genero = "Acció",
                            edadRecomendada = 12,
                            platforms = listOf("Amazon Prime"),
                            rating = 3.0f
                        ),
                        Titulo(
                            imagen = "tasm_dos.jpg",
                            nombre = "The Amazing Spider-Man 2",
                            description = "Spider-Man ha d'enfrontar-se a nous enemics, com Electro i el Follet Verd, mentre intenta protegir la ciutat de Nova York i mantenir la seva relació amb Gwen Stacy en aquesta espectacular seqüela plena d'acció i emoció.",
                            genero = "Acció",
                            edadRecomendada = 12,
                            platforms = listOf("Amazon Prime"),
                            rating = 2.6f
                        ),
                        Titulo(
                            imagen = "spiderman_homecoming.jpg",
                            nombre = "Spider-Man: Homecoming",
                            description = "Tom Holland debuta en solitari com a Spider-Man en aquesta pel·lícula on Peter Parker intenta demostrar el seu valor com a heroi mentre lluita contra el Vulture i equilibra la seva vida d'estudiant a l'institut.",
                            genero = "Acció",
                            edadRecomendada = 12,
                            platforms = listOf("Amazon Prime"),
                            rating = 3.2f
                        ),
                        Titulo(
                            imagen = "spiderman_ffh.jpg",
                            nombre = "Spider-Man: Far From Home",
                            description = "Després dels esdeveniments de Avengers: Endgame, Peter Parker emprèn un viatge a Europa amb els seus companys de classe. Però les vacances es veuen interrompudes quan apareix Mysterio, un nou personatge amb misterioses intencions.",
                            genero = "Acció",
                            edadRecomendada = 12,
                            platforms = listOf("Amazon Prime"),
                            rating = 3.1f
                        ),
                        Titulo(
                            imagen = "spiderman_nwh.jpg",
                            nombre = "Spider-Man: No Way Home",
                            description = "Quan la identitat de Spider-Man es revela al món, Peter Parker demana ajuda al Doctor Strange per restaurar el seu anonimat. Això desencadena una sèrie d'esdeveniments que porten a la col·lisió de diferents universos en una aventura èpica.",
                            genero = "Acció",
                            edadRecomendada = 13,
                            platforms = listOf("Amazon Prime"),
                            rating = 3.7f
                        ),
                        Titulo(
                            imagen = "tayvsm_serie.jpeg",
                            nombre = "El teu Amic i Veí Spider-Man",
                            description = "Sèrie animada que narra les aventures de Spider-Man mentre protegeix la ciutat de Nova York dels seus enemics més perillosos. Una història plena d'acció, humor i valors heroics per a tota la família.",
                            genero = "Animació",
                            edadRecomendada = 10,
                            platforms = listOf("Disney+"),
                            rating = 3.0f
                        ),
                        Titulo(
                            imagen = "DelReves.jpg",
                            nombre = "Del Revés",
                            description = "En aquesta emotiva pel·lícula d'animació de Pixar, seguim les emocions personificades d'una jove nena anomenada Riley. El film explora com les emocions com l'alegria, la tristesa, la ira, la por i el fàstic interactuen dins del cervell de Riley mentre afronta els canvis en la seva vida, destacant la importància de la tristesa com a part de la nostra experiència emocional.",
                            genero = "Animación, Aventura",
                            edadRecomendada = 6,
                            platforms = listOf("Disney+"),
                            rating = 4.0f
                        ),
                        Titulo(
                            imagen = "delrevesdos.jpg",
                            nombre = "Del Revés 2",
                            description = "En la seqüela de *Del Revés*, Riley i les seves emocions s'enfronten a nous reptes a mesura que creix i passa per noves etapes de la seva vida. Aquesta pel·lícula continua explorant la complexitat de les emocions humanes amb una nova perspectiva sobre l'auto-descobriment i l'adaptació a les situacions canviants.",
                            genero = "Animación, Aventura",
                            edadRecomendada = 6,
                            platforms = listOf("Disney+"),
                            rating = 4.0f
                        ),
                        Titulo(
                            imagen = "anatomia_de_grey.jpg",
                            nombre = "Grey's Anatomy",
                            description = "Una de les sèries més icòniques del gènere dramàtic que segueix la vida i els reptes de diversos metges residents en un hospital de Seattle. Amb un enfocament profund en les seves relacions personals i professionals, *Grey's Anatomy* explora temes com l'amor, la pèrdua, la responsabilitat i la sacrifici.",
                            genero = "Drama, Medicina",
                            edadRecomendada = 16,
                            platforms = listOf("Netflix"),
                            rating = 4.0f
                        ),
                        Titulo(
                            imagen = "FF1.jpeg",
                            nombre = "Fast & Furious",
                            description = "Un grup de conductors i lladres s'uneixen en una sèrie d'accions plenes d'adrenalina per dur a terme robatoris amb vehicles de gran potència. La relació entre Brian O'Conner, un policia infiltrat, i Dominic Toretto, un criminal respectat, es torna complicada a mesura que la lleialtat, l'amistat i la velocitat es posen a prova.",
                            genero = "Acción",
                            edadRecomendada = 16,
                            platforms = listOf("Netflix"),
                            rating = 4.0f
                        ),
                        Titulo(
                            imagen = "FF2.jpg",
                            nombre = "Fast & Furious 2",
                            description = "Després d'escapar de la seva vida anterior, Brian O'Conner es trasllada a Miami, on s'uneix a un antic amic per resoldre un cas d'alta velocitat. L'acció, les persecucions i les relacions tensen encara més la frontera entre la llei i el món criminal en aquest segon capítol de la saga.",
                            genero = "Acción",
                            edadRecomendada = 16,
                            platforms = listOf("Netflix"),
                            rating = 4.0f
                        ),
                        Titulo(
                            imagen = "FF3.jpg",
                            nombre = "Fast & Furious 3: Tòquio Drift",
                            description = "En aquest capítol, el jove Lucas es trasllada a Tòquio, on s'endinsa en el món del drift, una competició de cotxes de gran velocitat. Enfrontant-se a noves dinàmiques de velocitat i lleialtat, Lucas descobreix què significa ser part d'una família de conductors.",
                            genero = "Acción",
                            edadRecomendada = 16,
                            platforms = listOf("Netflix"),
                            rating = 4.0f
                        ),
                        Titulo(
                            imagen = "FF4.jpg",
                            nombre = "Fast & Furious 4",
                            description = "El retorn de Brian O'Conner i Dominic Toretto els porta a unir forces per resoldre un assassinat vinculat a l'automobilisme il·legal. La trama explora temes com la venjança, l'amistat i la lleialtat, mentre els dos herois han de combinar les seves habilitats per lluitar contra la corrupció i la violència.",
                            genero = "Acción",
                            edadRecomendada = 16,
                            platforms = listOf("Netflix"),
                            rating = 4.0f
                        ),
                        Titulo(
                            imagen = "FF5.jpg",
                            nombre = "Fast & Furious 5",
                            description = "Brian i Toretto s'uneixen a un grup de criminals per un robatori a gran escala, desafiant les lleis i la policia en una persecució internacional. Amb una nova incorporació a l'equip, els amics s'enfronten a desafiaments per una recompensa que podria canviar les seves vides.",
                            genero = "Acción",
                            edadRecomendada = 16,
                            platforms = listOf("Netflix"),
                            rating = 4.0f
                        ),
                        Titulo(
                            imagen = "FF6.jpg",
                            nombre = "Fast & Furious 6",
                            description = "Brian i Toretto treballen per desmantellar una xarxa criminal, mentre que l'amistat entre els membres de l'equip s'accentua. Amb el suport de l'Exèrcit, l'equip es prepara per l'acció més perillosa fins ara, amb l'objectiu de frenar un enfrontament fatal.",
                            genero = "Acción",
                            edadRecomendada = 16,
                            platforms = listOf("Netflix"),
                            rating = 4.0f
                        ),
                        Titulo(
                            imagen = "FF7.jpg",
                            nombre = "Fast & Furious 7",
                            description = "Després de la pèrdua d'un membre clau de l'equip, Brian i Toretto continuen lluitant contra la criminalitat. Amb la família reunida, es preparen per una última missió per venjar la mort d'un dels seus i per defensar les seves pròpies vides.",
                            genero = "Acción",
                            edadRecomendada = 16,
                            platforms = listOf("Netflix"),
                            rating = 4.0f
                        ),
                        Titulo(
                            imagen = "FF8.jpg",
                            nombre = "Fast & Furious 8",
                            description = "Dominic Toretto es veu temptat per una poderosa criminal, la qual fa trontollar la seva lleialtat cap a la seva família. Els membres de l'equip s'uneixen per aturar una nova amenaça global mentre intenten resoldre conflictes interns.",
                            genero = "Acción",
                            edadRecomendada = 16,
                            platforms = listOf("Netflix"),
                            rating = 4.0f
                        ),
                        Titulo(
                            imagen = "FF9.jpg",
                            nombre = "Fast & Furious 9",
                            description = "L'equip s'enfronta a un nou enemic: el germà perdut de Dom, Jakob. Amb l'amenaça de desestabilitzar el món, Dom, Brian i el seu equip s'uneixen una vegada més per aturar Jakob i salvar el món de la destrucció total. Les persecucions són més ràpides i perilloses que mai.",
                            genero = "Acción",
                            edadRecomendada = 16,
                            platforms = listOf("Netflix"),
                            rating = 4.0f
                        ),
                        Titulo(
                            imagen = "TLOR1.jpg",
                            nombre = "El Señor de los Anillos",
                            description = "La història de Frodo Baggins, un jove hobbit que s'embarca en una perillosa missió per destruir un anell màgic, acompanyat per un grup d'amics i aliats. Aquesta travessia s'emmarca en la Terra Mitjana, un món màgic amenaçat per les forces de la foscor liderades per Sauron.",
                            genero = "Fantasía",
                            edadRecomendada = 12,
                            platforms = listOf("HBO Max"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "TLOR2.jpg",
                            nombre = "El Señor de los Anillos: Las dos torres",
                            description = "Segona part de l'èpica trilogia, on Frodo i Sam lluiten per destruir l'anell mentre Aragorn, Legolas i Gimli intenten evitar que la guerra sobrevingui a la Terra Mitjana. La batalla per l'Anell de Poder es converteix en una lluita contra la tirania i la foscor que amenaça tot.",
                            genero = "Fantasía",
                            edadRecomendada = 12,
                            platforms = listOf("HBO Max"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "TLOR3.jpg",
                            nombre = "El Señor de los Anillos: El retorno del rey",
                            description = "La conclusió de la trilogia, on les forces de la foscor es preparen per l'última batalla per la dominació de la Terra Mitjana. Frodo i Sam arriben a les portes de la muntanya del foc mentre els altres combatents lluiten per la supervivència de les seves terres i la llibertat del poble.",
                            genero = "Fantasía",
                            edadRecomendada = 12,
                            platforms = listOf("HBO Max"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "Hobbit1.jpg",
                            nombre = "El Hobbit: Un viatge inesperat",
                            description = "Bilbo Baggins, un hobbit tranquil, s'uneix a un grup d'anans en una aventura per recuperar un tresor custodiat per un drac anomenat Smaug. El seu viatge està ple de perills, misteris i nous aliats, i en el camí descobrirà el valor que mai pensava tenir.",
                            genero = "Fantasía",
                            edadRecomendada = 12,
                            platforms = listOf("HBO Max"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "Hobbit2.jpg",
                            nombre = "El Hobbit: La desolació de Smaug",
                            description = "Bilbo i la companyia d'anans continuen la seva travessia per la Terra Mitjana per arribar a la muntanya on resideix el drac Smaug. En aquesta pel·lícula, els personatges s'enfronten a perills mortals i als efectes de la seva recerca, mentre Bilbo comença a descobrir el poder del seu anell màgic.",
                            genero = "Fantasía",
                            edadRecomendada = 12,
                            platforms = listOf("HBO Max"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "Hobbit3.jpg",
                            nombre = "El Hobbit: La batalla dels cinc exèrcits",
                            description = "La guerra per l'anell culmina en una gran batalla en la qual els destins de molts herois i terres queden en joc. La lluita entre anans, elfos, humans i orcs arriba al seu punt àlgid, mentre Bilbo descobreix la veritable dimensió del poder de l'anell.",
                            genero = "Fantasía",
                            edadRecomendada = 12,
                            platforms = listOf("HBO Max"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "Friends.jpg",
                            nombre = "Friends",
                            description = "Un grup d'amics de Nova York comparteixen les seves vides, alegries i dificultats mentre naveguen pels seus sentiments, relacions i carreres. Aquesta comèdia clàssica tracta temes d'amistat, amor i creixement personal amb molt d'humor i moments memorables.",
                            genero = "Comedia",
                            edadRecomendada = 12,
                            platforms = listOf("HBO Max"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "TheBigBangTheory.webp",
                            nombre = "The Big Bang Theory",
                            description = "Aquesta comèdia segueix a un grup de científics i les seves relacions amb el món que els envolta, incloent la seva interacció amb una jove veïna que entra a les seves vides de manera inesperada. Una barreja d'humor geek i situacions còmiques.",
                            genero = "Comedia",
                            edadRecomendada = 12,
                            platforms = listOf("HBO Max"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "Merli.jpg",
                            nombre = "Merlí",
                            description = "Merlí és un professor de filosofia que inspira als seus alumnes a pensar de manera crítica i a qüestionar la societat. A través de les seves classes i la seva forma única d'ensenyar, ajuda els estudiants a afrontar els seus propis dilemes personals.",
                            genero = "Comedia",
                            edadRecomendada = 12,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "pulseres_vermelles.webp",
                            nombre = "Pulseras Vermelles",
                            description = "La sèrie explica la història d'un grup de nois i noies que coincideixen en un hospital a causa de diverses malalties i parla de l'amistat i les ganes de viure i la voluntat de superar les seves malalties.",
                            genero = "Drama",
                            edadRecomendada = 12,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "Mic.jpg",
                            nombre = "Mic",
                            description = "El Mic és un nino fet de llana que sempre té ganes de jugar i descobrir-ho tot.",
                            genero = "Animación",
                            edadRecomendada = 6,
                            platforms = listOf("3cat"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "la_que_se_avecina.jpg",
                            nombre = "La que se avecina",
                            description = "La que se avecina és una comèdia que segueix les desventures d'una comunitat de veïns amb personalitats úniques i divertides. Les situacions disparatades i els personatges excèntrics faran que el públic es ragi de riure en cada episodi.",
                            genero = "Comedia",
                            edadRecomendada = 12,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "aqui_no_hay_quien_viva.jpg",
                            nombre = "Aquí no hay quien viva",
                            description = "Amb una estructura de comèdia de situacions, Aquí no hay quien viva retrata les vides d'una comunitat de veïns amb unes personalitats molt diferents. Els enfrontaments, conflictes i moments absurds són els protagonistes d'aquesta sèrie humorística.",
                            genero = "Comedia",
                            edadRecomendada = 12,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "Cuantame.jpg",
                            nombre = "Cuéntame",
                            description = "Cuéntame és una sèrie de drama que narra la vida d'una família espanyola a partir dels anys 60. A través dels ulls d'un jove, s'exploren els canvis socials i històrics que van marcar l'Espanya moderna, des de la dictadura fins a la democràcia.",
                            genero = "Drama",
                            edadRecomendada = 12,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "the_walking_dead.jpg",
                            nombre = "The Walking Dead",
                            description = "The Walking Dead és una sèrie de televisió desenvolupada per Frank Darabont i basada en la sèrie de còmics homònima creada per Robert Kirkman i Tony Moore.",
                            genero = "Drama",
                            edadRecomendada = 18,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "BreakingBad.jpg",
                            nombre = "Breaking Bad",
                            description = "Breaking Bad és una sèrie de drama que segueix un professor de química que es converteix en un traficant de drogues per assegurar el futur de la seva família. Amb un guió intens i personatges complexos, aquesta sèrie ha marcat un abans i un després en la televisió.",
                            genero = "Drama",
                            edadRecomendada = 18,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "PeakyBlinders.jpg",
                            nombre = "Peaky Blinders",
                            description = "Peaky Blinders segueix les aventures de la família Shelby a la postguerra mundial, mentre lluiten per consolidar el seu poder i influència en una ciutat plena de conflictes. La sèrie destaca pel seu estil visual, la seva trama captivadora i les seves actuacions destacades.",
                            genero = "Drama",
                            edadRecomendada = 18,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "barbie.jpg",
                            nombre = "Barbie",
                            description = "Barbie és una pel·lícula d'animació que segueix les aventures de la icònica nina en un món ple de màgia, amics i viatges inoblidables. Amb missatges d'autoestima i empoderament, és una opció perfecta per als més petits.",
                            genero = "Animació",
                            edadRecomendada = 6,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "Oppenheimer.jpg",
                            nombre = "Oppenheimer",
                            description = "Oppenheimer explora la vida de J. Robert Oppenheimer, el científic que va liderar el projecte Manhattan per desenvolupar l'arma atòmica durant la Segona Guerra Mundial. La pel·lícula tracta sobre dilemes morals i la responsabilitat de crear armes de destrucció massiva.",
                            genero = "Drama",
                            edadRecomendada = 18,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "Titanic.jpg",
                            nombre = "Titanic",
                            description = "Jack és un jove artista que guanya un passatge per viatjar a Amèrica al Titanic, el transatlàntic més gran i segur mai construït.",
                            genero = "Drama",
                            edadRecomendada = 12,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "ET.jpg",
                            nombre = "E.T.",
                            description = "Un petit extraterrestre d'un altre planeta queda abandonat a la Terra quan la seva nau se n'oblida. Està completament sol i espantat fins que l'Elliott, un nen de nou anys, el troba i decideix amagar-lo a casa per protegir-lo.",
                            genero = "Ciencia ficción",
                            edadRecomendada = 12,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "los-100-the-hundred.jpg",
                            nombre = "Els 100",
                            description = "Un apocalipsi nuclear destrueix la vida humana a la Terra. Els únics supervivents són els habitants de les estacions espacials internacionals. Tres generacions després, l'escassetat de recursos els obliga a prendre mesures desesperades.",
                            genero = "Ciencia ficción",
                            edadRecomendada = 12,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "Matilda.jpg",
                            nombre = "Matilda",
                            description = "Una nena desenvolupa una capacitat mental extraordinària, malgrat els pares descuidats i una directora abusiva.",
                            genero = "Fantasía",
                            edadRecomendada = 12,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "tiempo.jpg",
                            nombre = "Temps",
                            description = "Durant unes vacances en un paradís tropical, una família comença a percebre que a la recòndita platja triada per relaxar-se unes hores succeeixen algunes anomalies temporals.",
                            genero = "Drama",
                            edadRecomendada = 12,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "shameless.jpg",
                            nombre = "Shameless",
                            description = "Aquesta dramèdia, inspirada en una sèrie britànica, s'enfoca a uns germans de Chicago ia les batalles quotidianes que lliuren per viure amb el seu pare alcohòlic. William H. Macy va obtenir un premi SAG i Joan Cusack va guanyar un Emmy per les seves interpretacions a la sèrie.",
                            genero = "Drama",
                            edadRecomendada = 18,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "Grease.jpg",
                            nombre = "Grease",
                            description = "El rebel Danny Zuko i la innocent australiana Sandy mantenen un idil·li durant les vacances d'estiu, creient que no es tornaran a veure. No obstant, per a la sorpresa de tots dos, acaben estudiant junts al mateix institut durant el nou curs.",
                            genero = "Musical",
                            edadRecomendada = 12,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "Los_simpson.jpg",
                            nombre = "Los Simpson",
                            description = "La sèrie és una sàtira cap a la societat nord-americana que narra la vida i el dia a dia d'una família de classe mitjana d'aquest país (els membres del qual són Homer, Marge, Bart, Lisa i Maggie Simpson) que viu en un poble fictici anomenat Springfield.",
                            genero = "Animación",
                            edadRecomendada = 12,
                            platforms = listOf("Disney+"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "arcane.jpg",
                            nombre = "Arcane",
                            description = "Arcane és una sèrie de televisió per internet animada franco-nord-americana d'aventures i drama ambientada a l'univers de League of Legends.",
                            genero = "Animación",
                            edadRecomendada = 12,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "american_horror_story.jpg",
                            nombre = "American Horror Story",
                            description = "American Horror Story és una sèrie dramàtica amb pinzellades de terror, creada i produïda per Ryan Murphy i Brad Falchuk per a televisió",
                            genero = "Terror",
                            edadRecomendada = 18,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "Laia.jpg",
                            nombre = "Laia",
                            description = "Laia és una pel·lícula catalana de 2016, dirigida per Lluís Danés, basada en l'obra homònima de 1932 de Salvador Espriu. Va ser nominada al Gaudí a la millor pel·lícula per televisió de 2017.",
                            genero = "Drama",
                            edadRecomendada = 12,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "The_Rain.jpg",
                            nombre = "La pluja",
                            description = "Producció apocalíptica danesa on la pluja infecta tot allò que toca. Un virus que sembla afectar també el guió, inconsistent i fluctuant. Un nou apocalipsi assola la Terra i ho fa en forma dun virus que porta la pluja.",
                            genero = "Ciencia ficción",
                            edadRecomendada = 16,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "eden.jpg",
                            nombre = "Bienvenidos a Edén",
                            description = "Uns joves assisteixen a una festa exclusiva a una illa remota. Però el temptador paradís amb què es troben oculta secrets i paranys molt perillosos",
                            genero = "Drama",
                            edadRecomendada = 16,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "Scream1.webp",
                            nombre = "Scream 1",
                            description = "Un assassí en sèrie, amb màscara i disfressa negra, sembra el pànic entre els adolescents d?un poble californià. Paral·lelament, la jove Sidney Prescott passa un mal moment: fa un any que va morir la seva mare.",
                            genero = "Terror",
                            edadRecomendada = 18,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "Scream2.jpg",
                            nombre = "Scream 2",
                            description = "Dos anys després de la matança a Woodsboro, Sidney i Randy intenten reconstruir les seves vides en una altra ciutat. La seva tranquil·litat es veurà pertorbada per una onada de brutals assassinats comesos per un psicòpata emmascarat.",
                            genero = "Terror",
                            edadRecomendada = 18,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "Scream3.jpg",
                            nombre = "Scream 3",
                            description = "Sidney viu allunyada del món en una petita casa a les muntanyes, on intenta oblidar el passat. Fins que rep una misteriosa anomenada...",
                            genero = "Terror",
                            edadRecomendada = 18,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "Scream4.jpg",
                            nombre = "Scream 4",
                            description = "Deu anys després dels assassinats de Woodsbord, Sidney Prescott, convertida en una escriptora d'èxit, torna al seu poble natal, però el retorn anirà acompanyat d'una nova onada de crims perpetrats per un vell conegut.",
                            genero = "Terror",
                            edadRecomendada = 18,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "Scream5.jpg",
                            nombre = "Scream 5",
                            description = "25 anys després que una ratxa d'assassinats brutals commocionés la tranquil·la ciutat de Woodsboro, un nou assassí imitador s'ha posat la màscara de Ghostface per ressuscitar secrets del passat.",
                            genero = "Terror",
                            edadRecomendada = 18,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "Scream6.jpg",
                            nombre = "Scream 6",
                            description = "La trama principal de la pel·lícula segueix els quatre supervivents de la matança de Woodsboro del 2022, i com aquests intenten canviar d'aires per suportar el trauma que va canviar i va marcar les seves vides joves.",
                            genero = "Terror",
                            edadRecomendada = 18,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "Scream7.jpg",
                            nombre = "Scream 7",
                            description = "'Scream 7' porta de tornada Matthew Lillard, que va ser el primer Ghostface fa 30 anys. Tornen a la franquícia de terror dos vells coneguts de la saga, tots dos van resultar ser el Ghostface i tots dos semblava que havien mort: Matthew Lillard a la primera pel·lícula i Scott Foley a la tercera.",
                            genero = "Terror",
                            edadRecomendada = 18,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "EnolaHolmes.webp",
                            nombre = "Enola Holmes",
                            description = "Enola Holmes, una intrèpida jove que busca la seva mare, utilitza el seu brillant instint investigador per superar el seu germà Sherlock i ajudar un lord a la seva fugida.",
                            genero = "Misterio",
                            edadRecomendada = 12,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "La_teoraia_del_todo.jpg",
                            nombre = "La Teoria del Tot",
                            description = "Als 21 anys, Stephen, un brillant estudiant, rep un desolador diagnòstic: una malaltia neuronal motora atacarà els seus membres i habilitats, deixant-ho amb una capacitat de moviment i parla molt limitada, i una esperança de vida de dos anys.",
                            genero = "Drama",
                            edadRecomendada = 12,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "the_haunting_of_bly_manor.jpg",
                            nombre = "La Maladicció de Bly Manor",
                            description = "Morir no equival a desaparèixer. En aquest gòtic romanç, una au pair s'endinsa en un abisme d'esgarrifosos secrets... Continuació de \"La maledicció de Hill House\" a l'estil de \"American Horror Story\" o \"Channel Zero\", és a dir, comptant amb nous personatges i una història diferent. En aquesta ocasió, es basa en el clàssic d'Henry James \"Una altra volta de rosca\" (The Turn of the Screw), la millor adaptació al cinema del qual, \"Suspense\" (1961), és un dels grans clàssics del cinema de terror.",
                            genero = "Terror",
                            edadRecomendada = 16,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "vis_a_vis.jpg",
                            nombre = "Vis a Vis",
                            description = "Narra les vivències a la presó de Macarena, una jove fràgil i innocent que només ingressar a la presó es veu immersa en una difícil situació a la qual haurà d'aprendre a adaptar-se. A més del xoc que li suposa acabar de cop amb la seva agradable vida acomodada, aviat descobrirà que massa gent a la presó està darrere la pista de nou milions d'euros robats d'un furgó.",
                            genero = "Drama",
                            edadRecomendada = 16,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "las_chicas_del_cable.jpg",
                            nombre = "Las Chicas del Cable",
                            description = "Ambientada a la Barcelona dels anys 20, *Las Chicas del Cable* segueix la història de quatre dones que treballen en una companyia de telefonia, lluitant pels seus drets i per la seva llibertat en una societat dominada pels homes. A través de les seves vides, s'exploren temes com la igualtat de gènere, l'amistat i els conflictes personals.",
                            genero = "Drama",
                            edadRecomendada = 16,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "heartstopper.jpg",
                            nombre = "Heartstopper",
                            description = "Basada en la famosa sèrie de còmics, *Heartstopper* segueix la història d'amor entre Charlie, un noi que lluita amb la seva identitat sexual, i Nick, un company de classe que descobreix els seus propis sentiments. La sèrie explora temes com l'amistat, l'amor, l'autoacceptació i els desafiaments de ser jove en un món complex.",
                            genero = "Drama, Romance",
                            edadRecomendada = 16,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "la_primera_muerte.jpg",
                            nombre = "La Primera Mort",
                            description = "Dos joves, una vampira i una caçadora de vampirs, es veuen atrapades en una història d'amor prohibida mentre lluiten amb les seves pròpies identitats i els conflictes entre les seves famílies i mons enfrontats. La sèrie tracta temes com l'amor impossible, la identitat i les conseqüències de les decisions difícils.",
                            genero = "Drama, Fantasía",
                            edadRecomendada = 16,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "la_calle_del_terror_Parte_1.jpg",
                            nombre = "El carrer del Terror: Part 1",
                            description = "La primera part de ens introdueix en un grup d'amics que descobreixen els horrors ocults darrere d'una sèrie de tràgics esdeveniments en un poble aparentment tranquil. Mentre investiguen, es veuen atrapats en una antiga maledicció que els amenaça a tots.",
                            genero = "Terror",
                            edadRecomendada = 18,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "la_calle_del_terror_Parte_2.jpg",
                            nombre = "El carrer del Terror: Part 2",
                            description = "La segona part segueix els personatges mentre intenten escapar de la maledicció que assola el poble. A mesura que desenterren més secrets foscos, es veuen atrapats en una lluita desesperada per sobreviure i trencar el cicle de terror que els persegueix.",
                            genero = "Terror",
                            edadRecomendada = 18,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "la_calle_del_terror_Parte_3.jpg",
                            nombre = "El carrer del Terror: Part 3",
                            description = "En la tercera part, les revelacions arriben al seu màxim punt. Els personatges han de fer front als seus propis dimonis interiors i als horrors més grans, mentre la maledicció finalment arriba a la seva culminació. La batalla pel futur del poble es torna més intensa i mortal.",
                            genero = "Terror",
                            edadRecomendada = 18,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "la_monja_guerrera.jpg",
                            nombre = "La Monja Guerrera",
                            description = "Una jove guerrera, marcada pel seu passat, es veu atrapada entre el món humà i el sobrenatural. Des de la seva formació com a monja, lluita per protegir el món de les forces del mal mentre descobreix els secrets ocults del seu origen.",
                            genero = "Acción",
                            edadRecomendada = 16,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "elite.webp",
                            nombre = "Élite",
                            description = "Una sèrie que segueix la vida d'un grup d'adolescents de diferents classes socials que estudien en una escola elitista. La trama gira al voltant de relacions complicades, secrets, traïcions i un assassinat que sacseja la comunitat escolar.",
                            genero = "Drama",
                            edadRecomendada = 16,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "paquita_salas.jpg",
                            nombre = "Paquita Salas",
                            description = "Paquita Salas és una agent de talent a Madrid que lluita per mantenir el seu negoci en peu. La sèrie barreja humor i drama mentre Paquita intenta trobar oportunitats per als seus clients, tot i les seves pròpies inseguretats.",
                            genero = "Drama",
                            edadRecomendada = 16,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "ni_una_mas.jpg",
                            nombre = "Ni una más",
                            description = "La sèrie aborda el drama d'una jove que, després de ser víctima d'un acte de violència, es converteix en un símbol de lluita per la justícia i els drets de les dones, exposant els problemes socials i les dificultats de fer front a la tragèdia.",
                            genero = "Drama",
                            edadRecomendada = 16,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "dahmer.jpg",
                            nombre = "Dahmer",
                            description = "Basada en fets reals, explora la vida de Jeffrey Dahmer, un dels més infames assassins en sèrie dels Estats Units. La sèrie aprofundeix en la psicologia de Dahmer i els seus crims, així com en el dolor de les seves víctimes i les seves famílies.",
                            genero = "Drama",
                            edadRecomendada = 18,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "Muaneca_rusa.jpg",
                            nombre = "Muñeca Rusa",
                            description = "Nadia es veu atrapada en un bucle temporal, revivint el mateix dia una i altra vegada. A mesura que intenta descobrir la causa d'aquest cicle, explora la seva vida, els seus errors i les seves relacions, mentre busca una sortida a aquest estrany joc de la mort.",
                            genero = "Drama, Comèdia, Misteri",
                            edadRecomendada = 16,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "Cortar_por_la_linea_de_puntos.jpg",
                            nombre = "Tallar per la Línia de Punts",
                            description = "Aquesta sèrie narra la història de diversos joves que lluiten per trobar el seu lloc en un món que no els comprèn. Les seves vides es creuen quan es veuen forçats a fer front a les seves pors, les seves frustracions i les seves ambicions, buscant la seva pròpia veritat en un entorn ple de tensions socials.",
                            genero = "Drama",
                            edadRecomendada = 16,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "invincible.jpg",
                            nombre = "Invencible",
                            description = "Una sèrie d'animació per a adults sobre un jove superheroi que descobreix la dura realitat de la seva herència.",
                            genero = "Drama",
                            edadRecomendada = 16,
                            platforms = listOf("Amazon Prime"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "IntoTheCosmos.jpg",
                            nombre = "Into the Cosmos",
                            description = "Una sèrie documental que ens porta a través de l'espai exterior, explorant els misteris de l'univers i els avenços científics més recents. Cada episodi ofereix una visió fascinant dels descobriments sobre planetes, estels i la vida fora de la Terra.",
                            genero = "Drama, Documental",
                            edadRecomendada = 16,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "the_umbrella_academy.jpg",
                            nombre = "The Umbrella Academy",
                            description = "Una família disfuncional de superherois adoptats, cada un amb poders especials, es reuneix després de la mort del seu pare adoptiu per resoldre el misteri de la seva mort i evitar una catàstrofe mundial. L'acció, el drama i l'humor es combinen en una trama imprevisible.",
                            genero = "Acción, Ciencia Ficción",
                            edadRecomendada = 16,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "atipico.jpg",
                            nombre = "Atípico",
                            description = "Segueix la vida d'un jove amb el síndrome d'Asperger mentre intenta comprendre les relacions humanes i trobar el seu camí cap a l'adultesa. La sèrie explora temes d'autoacceptació, la dinàmica familiar i l'amistat.",
                            genero = "Drama",
                            edadRecomendada = 16,
                            platforms = listOf("Netflix"),
                            rating = 3.5f
                        ),
                        Titulo(
                            imagen = "desencanto.jpg",
                            nombre = "Desencanto",
                            description = "Una sèrie animada que segueix les aventures de Bean, una princesa rebel, i els seus dos inseparables companys en un regne màgic ple d'humor irreverent. Creat per Matt Groening, la sèrie combina elements de fantasia amb una crítica sarcàstica a la societat.",
                            genero = "Animación, Comèdia",
                            edadRecomendada = 12,
                            platforms = listOf("Netflix"),
                            rating = 3.5f
                        ),
                        Titulo(
                            imagen = "HPChamberSecrets.jpg",
                            nombre = "Harry Potter i la cambra secreta",
                            description = "Harry Potter torna a Hogwarts per segon any i descobreix que algú ha obert la Cambra Secreta, posant en perill els estudiants del col·legi. Juntament amb els seus amics, haurà de desentranyar el misteri i enfrontar-se a una força fosca.",
                            genero = "Fantasía",
                            edadRecomendada = 12,
                            platforms = listOf("HBO Max"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "HPDeathlyHallows.jpg",
                            nombre = "Harry Potter i les relíquies de la mort: Part 1",
                            description = "Harry, Ron i Hermione surten de Hogwarts i comencen una perillosa missió per destruir les peces del llinatge del malvat Lord Voldemort. La seva amistat es veu posada a prova mentre s'enfronten a nombrosos perills.",
                            genero = "Fantasía",
                            edadRecomendada = 12,
                            platforms = listOf("HBO Max"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "HPDeathlyHallows2.jpg",
                            nombre = "Harry Potter i les relíquies de la mort: Part 2",
                            description = "La lluita final contra Lord Voldemort arriba a la seva culminació. Harry, amb l'ajuda dels seus amics, ha de lluitar per salvar el món màgic i destruir Voldemort per sempre.",
                            genero = "Fantasía",
                            edadRecomendada = 12,
                            platforms = listOf("HBO Max"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "HPGobletFire.jpg",
                            nombre = "Harry Potter i el calze de foc",
                            description = "Harry és escollit de manera inesperada per competir en el Torneig dels Tres Mags, una competició perillosa que posa a prova les seves habilitats màgiques. Mentre s'enfronta a proves mortalment difícils, una amenaça fosca es fa cada vegada més present.",
                            genero = "Fantasía",
                            edadRecomendada = 12,
                            platforms = listOf("HBO Max"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "HPOrderPhooenix.jpg",
                            nombre = "Harry Potter i les relíquies de la mort: Part 1",
                            description = "Harry forma part de l'Ordre del Fènix, un grup secret que lluita contra el retorn de Lord Voldemort. Enfrontant-se a la incredulitat del Ministeri de Màgia i a les seves pròpies lluites internes, Harry haurà de preparar-se per a una guerra imminent.",
                            genero = "Fantasía",
                            edadRecomendada = 12,
                            platforms = listOf("HBO Max"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "HPPrince.jpg",
                            nombre = "Harry Potter i el misteri del princep",
                            description = "Harry descobreix secrets del passat de Lord Voldemort gràcies a un antic llibre màgic. Mentrestant, la guerra contra les forces fosques es intensifica, i en Harry haurà de prendre decisions difícils que marcaran el seu destí.",
                            genero = "Fantasía",
                            edadRecomendada = 12,
                            platforms = listOf("HBO Max"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "HPPrisonerAzkaban.jpg",
                            nombre = "Harry Potter and the Prisoner of Azkaban",
                            description = "Harry descobreix que un presoner fugit, Sirius Black, ha escapat de la presó d'Azkaban i sembla estar buscant-lo. En el seu tercer any a Hogwarts, en Harry revela secrets del seu passat i es veu enfrontat a nous desafiaments màgics.",
                            genero = "Fantasía",
                            edadRecomendada = 12,
                            platforms = listOf("HBO Max"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "HPSorcerer'sStone.jpg",
                            nombre = "Harry Potter i la pedra filosofal",
                            description = "Un nen orfe, Harry Potter, descobreix que és un mag quan rep una carta per estudiar a Hogwarts. En el seu primer any a l'escola de màgia, es fa amics i enfronta perills mentre desvetlla els misteris al voltant de la màgica Pedra Filosofal.",
                            genero = "Fantasía",
                            edadRecomendada = 12,
                            platforms = listOf("HBO Max"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "winx.jpg",
                            nombre = "Winx",
                            description = "Un grup d'amigues màgiques es reuneixen per lluitar contra forces fosques mentre naveguen per les seves pròpies vides i els seus poders. Bloom, una jove que descobreix els seus poders màgics, lidera el grup per protegir el seu món i desvetllar secrets sobre la seva pròpia família.",
                            genero = "Fantasía, Drama",
                            edadRecomendada = 12,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        ),
                        Titulo(
                            imagen = "gambito_de_dama.jpg",
                            nombre = "Gambito de dama",
                            description = "Beth Harmon, una jove prodigi del ajedrez, lluita per dominar el món dels escacs mentre fa front a les seves pròpies inseguretats i problemes personals. La sèrie segueix la seva evolució, des de la seva infància fins a la maduresa, tot mentre es converteix en una de les jugadores més brillants del món.",
                            genero = "Drama",
                            edadRecomendada = 12,
                            platforms = listOf("Netflix"),
                            rating = 4f
                        )
                    )
                    val titulosACrear = titulosIniciales.filter { titulo ->
                        titulosExistentes.none { it.nombre == titulo.nombre }
                    }
                    for (titulo in titulosACrear) {
                        api.createTitulo(titulo)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private suspend fun cargarBarraProgreso(tiempoCarga: Long) {
        val tiempoTotal = if (tiempoCarga < 2000) 2000 else tiempoCarga
        val interval = 50
        var progress = 0
        withContext(Dispatchers.Main) {
            while (progress <= 100) {
                progressBar.progress = progress
                progress += interval
                delay(interval.toLong())
                progress += (100 * interval / tiempoTotal).toInt()
            }
        }
    }
}