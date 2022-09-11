import java.util.ArrayList;

/**
 * The test class TrainTest.
 *
 * @author  Lynn Marshall
 * @version May 2015
 * 
 * @author  Ahmed Ali
 * @version March 8th, 2022
 */
public class TrainTest extends junit.framework.TestCase
{
    private Train aTrain;
    private Car car1;
    private Car car2;
    private Car car3;
    private Car car4;
    
    /**
     * Default constructor for test class TrainTest
     */
    public TrainTest()
    {
        aTrain = new Train();
        
        car1 = new Car(1250, true);
        aTrain.addCar(car1);
        
        car2 = new Car(1300, false);
        aTrain.addCar(car2);
        
        car3 = new Car(1740, false);
        aTrain.addCar(car3);
        
        car4 = new Car(2222, true); // 4th car (business)
        aTrain.addCar(car4);
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    protected void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    protected void tearDown()
    {
    }
 
    public void testCreateEmptyTrain()
    {
        Train emptyTrain = new Train();
        
        /* Verify that a new train has no cars. */
        assertEquals(0, emptyTrain.cars().size());
    }
    
    public void testAddCar()
    {
        ArrayList<Car> cars = aTrain.cars();
        assertEquals(4, cars.size()); // 4 cars are added to the train
        
        
        /* Verify that each car added to the train was placed at
         * the end of the list.
         */
        
        /* Important - assertSame() does not compare the Car objects 
         * referred to by car1 and aCar to detemine if they are equal
         * (have the same state). It verifies that car1 an aCar refer to
         * the same object; i.e., that the Car (reference) retrieved by get(0)
         * is the first first that was added to the ArrayList.
         */
        assertSame(car1, cars.get(0));
        assertSame(car2, cars.get(1));
        assertSame(car3, cars.get(2));  
        assertSame(car4, cars.get(3));      
    }
        
    public void testIssueTicket()
    {
        /* Book all the seats in the business-class car. */
        for (int i = 0; i <Car.BUSINESS_SEATS; i++) {
            assertTrue(aTrain.issueTicket(true));
        }
        /* Book all the seats in 2nd business-class car. */
        for (int i = 0; i <Car.BUSINESS_SEATS; i++) {
            assertTrue(aTrain.issueTicket(true));
        }
        
        /* Attempt to book one more business-class seat, even
         * though they are all booked.
         */
        assertFalse(aTrain.issueTicket(true));        
 
        ArrayList<Car> cars = aTrain.cars();
        
        for (int i = 0; i < Car.BUSINESS_SEATS; i++) {
            assertTrue(cars.get(0).seats()[i].isBooked());
        }
        
        for (int i = 0; i < Car.ECONOMY_SEATS; i++) {
            assertFalse(cars.get(1).seats()[i].isBooked());
        }
        
        for (int i = 0; i < Car.ECONOMY_SEATS; i++) {
            assertFalse(cars.get(2).seats()[i].isBooked());
        }
        
        for (int i = 0; i < Car.BUSINESS_SEATS; i++) {
            assertTrue(cars.get(3).seats()[i].isBooked());
        }
        
        /* Book all the seats in the first economy-class car. */
        for (int i = 0; i <Car.ECONOMY_SEATS; i++) {
            assertTrue(aTrain.issueTicket(false));
        }
        
        cars = aTrain.cars();
        
        for (int i = 0; i < Car.BUSINESS_SEATS; i++) {
            assertTrue(cars.get(0).seats()[i].isBooked());
        }
        
        for (int i = 0; i < Car.ECONOMY_SEATS; i++) {
            assertTrue(cars.get(1).seats()[i].isBooked());
        }
        
        for (int i = 0; i < Car.ECONOMY_SEATS; i++) {
            assertFalse(cars.get(2).seats()[i].isBooked());
        }  
        
        /* Book all the seats in the second economy-class car. */
        for (int i = 0; i <Car.ECONOMY_SEATS; i++) {
            assertTrue(aTrain.issueTicket(false));
        }
        
        /* check that all seats are now booked */
        for (int i = 0; i < Car.BUSINESS_SEATS; i++) {
            assertTrue(cars.get(0).seats()[i].isBooked());
        }
        
        for (int i = 0; i < Car.ECONOMY_SEATS; i++) {
            assertTrue(cars.get(1).seats()[i].isBooked());
        }
        
        for (int i = 0; i < Car.ECONOMY_SEATS; i++) {
            assertTrue(cars.get(2).seats()[i].isBooked());
        }  
        
        /* Try to book another business class seat (fails)*/
        assertFalse(aTrain.issueTicket(true));
        /* Try to book another economy class seat (fails)*/
        assertFalse(aTrain.issueTicket(false));
    }
    
    public void testCancelTicket()
    {
        /* Book all the seats in the business-class car. */
        for (int i = 0; i <Car.BUSINESS_SEATS; i++) {
            assertTrue(aTrain.issueTicket(true));
        }
        /* Book half the seats in the 2nd business-class car. */
        for (int i = 0; i <Car.BUSINESS_SEATS / 2; i++) {
            assertTrue(aTrain.issueTicket(true));
        }
        
        /* Book all the seats in the first economy-class car. */
        for (int i = 0; i <Car.ECONOMY_SEATS; i++) {
            assertTrue(aTrain.issueTicket(false));
        }
        
        ArrayList<Car> cars = aTrain.cars();
        
        assertTrue(aTrain.cancelTicket(1300, 4));
        assertFalse(cars.get(1).seats()[3].isBooked());        
        
        /* Cancel ticket in a non-existent car. */
        assertFalse(aTrain.cancelTicket(1500, 7));
        
        /* Cancel ticket in a non-existent seat. */
        assertFalse(aTrain.cancelTicket(1300, 54));
        
        /* Cancel ticket for a seat that hasn't been booked. */
        assertFalse(aTrain.cancelTicket(1740, 21));
        assertFalse(cars.get(2).seats()[20].isBooked());        
        
        /* Attempt to cancel the same ticket twice. */
        assertTrue(aTrain.cancelTicket(1250, 11));
        assertFalse(cars.get(0).seats()[10].isBooked());
        
        assertFalse(aTrain.cancelTicket(1250, 11));   
        assertFalse(cars.get(0).seats()[10].isBooked());         
        
        
        /* Cancel ticket for a seat that hasn't been booked in the 2nd
         * business car. */
        assertFalse(aTrain.cancelTicket(2222, 16));
        assertFalse(cars.get(3).seats()[15].isBooked());
        
        /* Cancel ticket for a seat that has been booked in the 2nd
         * business car. */
        assertTrue(aTrain.cancelTicket(2222, 15));
        assertFalse(cars.get(3).seats()[14].isBooked()); 
    }
    
    public void testBookCancelTicket()
    {
        /* Book all the seats in the business-class car. */
        for (int i = 0; i <Car.BUSINESS_SEATS; i++) {
            assertTrue(aTrain.issueTicket(true));
        }
        /* Book half the seats in the 2nd business-class car. */
        for (int i = 0; i <Car.BUSINESS_SEATS / 2; i++) {
            assertTrue(aTrain.issueTicket(true));
        }
        /* Book all the seats in the first economy-class car. */
        for (int i = 0; i <Car.ECONOMY_SEATS; i++) {
            assertTrue(aTrain.issueTicket(false));
        }
        
        
        /* Cancel ticket for 3 seats that have been booked in 1st economy car. */
        assertTrue(aTrain.cancelTicket(1300, 21));
        assertFalse(aTrain.cars().get(1).seats()[20].isBooked());
        assertTrue(aTrain.cancelTicket(1300, 22));
        assertFalse(aTrain.cars().get(1).seats()[21].isBooked());
        assertTrue(aTrain.cancelTicket(1300, 11));
        assertFalse(aTrain.cars().get(1).seats()[10].isBooked());
        
        /* Book ticket for 4 seats in economy car, 3 in 1st and 1 in 2nd. */
        assertTrue(aTrain.issueTicket(false));
        assertTrue(aTrain.cars().get(1).seats()[10].isBooked());
        assertTrue(aTrain.issueTicket(false));
        assertTrue(aTrain.cars().get(1).seats()[20].isBooked());
        assertTrue(aTrain.issueTicket(false));
        assertTrue(aTrain.cars().get(1).seats()[21].isBooked());
        assertTrue(aTrain.issueTicket(false));
        assertTrue(aTrain.cars().get(2).seats()[0].isBooked());
        
        /*Cancel three business car tickets: one in the first business car and
        *two near the front of the second.*/
        assertTrue(aTrain.cancelTicket(1250, 21));
        assertFalse(aTrain.cars().get(0).seats()[20].isBooked());
        assertTrue(aTrain.cancelTicket(2222, 5));
        assertFalse(aTrain.cars().get(3).seats()[4].isBooked());
        assertTrue(aTrain.cancelTicket(2222, 15));
        assertFalse(aTrain.cars().get(3).seats()[14].isBooked());
        
        /* Book ticket for 4 seats in business car, 3 in 1st and 1 in 2nd. */
        assertTrue(aTrain.issueTicket(true));
        assertTrue(aTrain.cars().get(1).seats()[20].isBooked());
        assertTrue(aTrain.issueTicket(true));
        assertTrue(aTrain.cars().get(1).seats()[4].isBooked());
        assertTrue(aTrain.issueTicket(true));
        assertTrue(aTrain.cars().get(1).seats()[14].isBooked());
        assertTrue(aTrain.issueTicket(true));
        assertTrue(aTrain.cars().get(3).seats()[15].isBooked());
    }
}
