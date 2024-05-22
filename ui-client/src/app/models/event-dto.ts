export interface EventDTO {
  id: number;
  userId: string;
  eventName: string;
  eventTime: string; // Assuming the API returns a string date
}