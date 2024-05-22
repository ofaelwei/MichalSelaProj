import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { Picture } from './picture.entity';

@Injectable()
export class PicturesService {
  constructor(
    @InjectRepository(Picture)
    private picturesRepository: Repository<Picture>,
  ) {}

  async create(userId: string, timestamp: Date, photo: Buffer): Promise<Picture> {
    const picture = this.picturesRepository.create({ userId, timestamp, photo });
    return this.picturesRepository.save(picture);
  }

  findAll(): Promise<Picture[]> {
    return this.picturesRepository.find();
  }
}
