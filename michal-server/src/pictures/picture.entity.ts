import { Entity, Column, PrimaryGeneratedColumn } from 'typeorm';

@Entity()
export class Picture {
  @PrimaryGeneratedColumn()
  id: number;

  @Column()
  userId: string;

  @Column()
  timestamp: Date;

  @Column('longblob')  // Use 'longblob' for MySQL
  photo: Buffer;
}
