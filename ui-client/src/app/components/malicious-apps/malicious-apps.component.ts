import { Component, OnInit } from '@angular/core';

interface App {
  header: string;
  description: string;
  security: 'Sespicuess' | 'Alert' | 'needToCheck';
}

@Component({
  selector: 'app-malicious-apps',
  templateUrl: './malicious-apps.component.html',
  styleUrls: ['./malicious-apps.component.css']
})
export class MaliciousAppsComponent implements OnInit {
  apps: App[] = [
    {
      header: "WhatsApp",
      description: "Your WhatsApp is open on 3 devices",
      security: "Sespicuess"
    },
    {
      header: "Facebook",
      description: "Someone deleted 30 friends yesterday",
      security: "Alert"
    },
    {
      header: "Messenger",
      description: "Someone looked at your Messenger at 3 AM",
      security: "needToCheck"
    }
  ];

  constructor() { }

  ngOnInit(): void { }

  getSecurityIcon(security: string): string {
    switch (security) {
      case 'Sespicuess':
        return 'pi pi-question-circle';
      case 'Alert':
        return 'pi pi-exclamation-triangle';
      case 'needToCheck':
        return 'pi pi-exclamation-circle';
      default:
        return '';
    }
  }

  getSecurityColor(security: string): string {
    switch (security) {
      case 'Sespicuess':
        return 'orange';
      case 'Alert':
        return 'red';
      case 'needToCheck':
        return 'green';
      default:
        return '';
    }
  }
}
