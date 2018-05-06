package ro.vichim.test.testdoubles.acceptance;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features="classpath:BigRoomReservations.feature")
public class RunCukesTest {

}
