import { Test, TestingModule } from '@nestjs/testing';
import { AppSessionService } from './app-session.service';

describe('AppSessionService', () => {
  let service: AppSessionService;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [AppSessionService],
    }).compile();

    service = module.get<AppSessionService>(AppSessionService);
  });

  it('should be defined', () => {
    expect(service).toBeDefined();
  });
});
