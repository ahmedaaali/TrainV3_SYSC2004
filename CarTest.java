/**
 * The test class CarTest.
 *
 * @author  Lynn Marshall, SCE
 * @version 1.2 May 1st, 2015
 * 
 * @author  Ahmed Ali
 * @version March 8th, 2022
 */
public class CarTest extends junit.framework.TestCase
{
    /**
     * Default constructor for test class CarTest
     */
    public CarTest()
    {
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
    
    /**
     * Creates a buisness car and checks if it works.
     */
    public void testCreateBusinessCar()
    {
        Car aCar = new Car(1385, true);
        Seat[] seats = aCar.seats();
        
        /* Verify that the car has the right number of seats. */
        assertEquals(Car.BUSINESS_SEATS, seats.length);
        
        /* Verify that each seat has the correct number and price. */
        for (int i = 0; i < seats.length; i++) {
            assertEquals(i+1, seats[i].number());
            assertEquals(Car.BUSINESS_SEAT_COST, seats[i].price());
        }
    }
    
    /**
     * Creates an economy car and checks if it works.
     */
    public void testCreateEconomyCar()
    {
        Car aCar = new Car(1400, false);
        Seat[] seats = aCar.seats();
        
        /* Verify that the car has the right number of seats. */        
        assertEquals(Car.ECONOMY_SEATS, seats.length);
        
        /* Verify that each seat has the correct number and price. */       
        for (int i = 0; i < seats.length; i++) {
            assertEquals(i+1, seats[i].number());
            assertEquals(Car.ECONOMY_SEAT_COST, seats[i].price());
        }
    }    
    
    /**
     * Tests the if the ID's of both economy and buisness cars work.
     */
    public void testID()
    {
         Car aCar;
         aCar= new Car(1385, true);
         assertEquals(1385, aCar.id());
         aCar = new Car(1400, false);
         assertEquals(1400, aCar.id());
    }
    
    /**
     * Tests if the method works.
     */
    public void testIsBusinessClass()
    {
         Car aCar;
         aCar = new Car(1385, true);
         assertTrue(aCar.isBusinessClass());
         aCar = new Car(1400, false);
         assertFalse(aCar.isBusinessClass()); 
    }
    
    /**
     * Creates a buisness car and an economy car and checks if they both
     * can book seats.
     */
    public void testBookNextSeat()
    {
        Car aCar;
        aCar = new Car(1234, true);
        Car bCar;
        bCar = new Car(5678, false); // new economy car
        
        Seat[] seats = aCar.seats();
        Seat[] bSeats = bCar.seats(); // seats array for economy car
        
        /* Verify that no seats are booked. */
        for (int i = 0; i < seats.length; i++) {
            assertFalse(seats[i].isBooked());
        }        
        
        // Verify no seats are booked in economy car
        for (int i = 0; i < bSeats.length; i++) {
            assertFalse(bSeats[i].isBooked());
        }  
        /* Verify that the seats are booked consecutively,
         * starting with Seat #1.
         */
        for (int i = 0; i < seats.length; i++) {
            seats = aCar.seats();
            assertFalse(seats[i].isBooked()); // not booked
            assertTrue(aCar.bookNextSeat()); // book it
            assertTrue(seats[i].isBooked()); // now booked
            if (i!=seats.length-1) {
                assertFalse(seats[i+1].isBooked()); // but next isn't
            }
        }
        // Same for economy car
        for (int i = 0; i < bSeats.length; i++) {
            bSeats = bCar.seats();
            assertFalse(bSeats[i].isBooked()); // not booked
            assertTrue(bCar.bookNextSeat()); // book it
            assertTrue(bSeats[i].isBooked()); // now booked
            if (i!=bSeats.length-1) {
                assertFalse(bSeats[i+1].isBooked()); // but next isn't
            }
        }
        
        /* Try to book a seat now that all the seats have been booked. */
        assertFalse(aCar.bookNextSeat());
        assertFalse(bCar.bookNextSeat()); // false for booking next seat on econmy car
    }
    
    /**
     * Creates a buisness car and an economy car and checks if they both
     * can cancel seats.
     */
    public void testCancelSeat()
    {
        Car aCar;
        aCar = new Car(1234, true);
        Car eCar;
        eCar = new Car(5678, false); // new economy car
        
        Seat[] seats = aCar.seats();
        Seat[] eSeats = eCar.seats(); // seats array for economy car
        
        /* Cancel seat 0. cancelSeat() should return false, as there
         * is no seat 0.
         */
        assertFalse(aCar.cancelSeat(0));
        assertFalse(eCar.cancelSeat(0));
        
        /* Try cancelling a seat whose number is one higher than 
         * the highest valid seat number (seats.length - 1). 
         * cancelSeat() should return false.
         */
        assertFalse(aCar.cancelSeat(seats.length));
        assertFalse(eCar.cancelSeat(eSeats.length));
        
        /* Try cancelling all the seats in the car, even though 
         * they haven't been booked. cancelSeat() should 
         * return false.
         */
        for (int i = 0; i < seats.length; i++) {
            assertFalse(aCar.cancelSeat(i+1));
        }
        // For economy
        for (int i = 0; i < eSeats.length; i++) {
            assertFalse(eCar.cancelSeat(i+1));
        }
        
        /* Book all the seats */
        for (int i = 0; i < seats.length; i++) {
            aCar.bookNextSeat();
        }  
        // For economy
        for (int i = 0; i < eSeats.length; i++) {
            eCar.bookNextSeat();
        }  
        
        /* Try cancelling all the seats in the car.
         */
        for (int i = 0; i < seats.length; i++) {
            assertTrue(aCar.cancelSeat(i+1));
        } 
        // For economy
        for (int i = 0; i < eSeats.length; i++) {
            assertTrue(eCar.cancelSeat(i+1));
        } 
        
        //Try cancelling seat 50
        assertFalse(aCar.cancelSeat(50));
        assertFalse(eCar.cancelSeat(50));
        
        /* In case seat numbers are off, try some more tests.
         */
        Car bCar;
        bCar = new Car (4321,false);
        // book 2 seats
        assertTrue(bCar.bookNextSeat());
        assertTrue(bCar.bookNextSeat());
        // try to cancel the 3rd (not booked)
        assertFalse(bCar.cancelSeat(3));
        // cancel the 1st and 2nd (were both booked)
        assertTrue(bCar.cancelSeat(1));
        assertTrue(bCar.cancelSeat(2));
    }      
}
