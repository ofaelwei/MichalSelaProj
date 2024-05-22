import { Entity, Column, PrimaryGeneratedColumn } from 'typeorm';

@Entity()
export class AppSession {
  @PrimaryGeneratedColumn()
  id: number;

  @Column()
  userId: string;

  @Column()
  appName: string;

  @Column()
  startSession: Date;

  @Column()
  endSession: Date;
}
