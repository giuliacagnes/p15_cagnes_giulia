package it.unige.se.battaglianavale;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CellaTest.class, GiocatoreTest.class, NaveTest.class, UtenteRegistratoTest.class, Partita.class })
public class AllTests {

}
