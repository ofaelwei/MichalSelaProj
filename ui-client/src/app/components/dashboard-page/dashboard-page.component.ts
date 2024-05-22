import { Component, OnInit } from '@angular/core';
import { EventDTO } from '../../models/event-dto'; // Import the EventDTO class
  

@Component({
  selector: 'app-dashboard-page',
  templateUrl: './dashboard-page.component.html',
  styleUrl: './dashboard-page.component.css'
})
export class DashboardPageComponent implements OnInit {

  event: EventDTO | undefined; // Declare a variable of type EventDTO


  ngOnInit() {


          // Initialize the event variable
    this.event = {
      id: 1,
      userId: 'user123',
      eventName: 'login',
      eventTime: '2024-05-22T12:00:00.000Z'
    };
  }
}