import { Component } from '@angular/core';
import { AlarmDTO } from '../../models/alarm.dto';


@Component({
  selector: 'app-alarms-tab',
  templateUrl: './alarms-tab.component.html',
  styleUrl: './alarms-tab.component.css'
})
export class AlarmsTabComponent {

  alarms: AlarmDTO[] = [
    { name: 'Facebook', comment: "Browsing", time: new Date('2024-05-23T23:30:00'), severity: 'Low' },
    { name: 'Facebook', comment: "Unfreind Avi.Gispan", time: new Date('2024-05-23T23:33:00') , severity: 'High' },
    { name: 'Whatsapp', comment: "Browsing DM", time: new Date('2024-05-23T23:35:00'), severity: 'Medium' },
  ];




}
