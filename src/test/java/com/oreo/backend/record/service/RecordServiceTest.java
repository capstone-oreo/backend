package com.oreo.backend.record.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.mock;

import com.oreo.backend.record.document.Record;
import com.oreo.backend.record.exception.RecordNotFoundException;
import com.oreo.backend.record.repository.RecordRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RecordServiceTest {

    @InjectMocks
    RecordService recordService;

    @Mock
    RecordRepository recordRepository;

    @Nested
    class DeleteRecord {

        @Test
        @DisplayName("분석 기록을 삭제한다.")
        void deleteRecord() {
            //given
            String fileId = "12345";
            Record mockRecord = mock(Record.class);
            given(recordRepository.findByFile_Id(fileId)).willReturn(Optional.of(mockRecord));
            willDoNothing().given(recordRepository).delete(mockRecord);

            //when
            recordService.deleteRecord(fileId);
        }

        @Test
        @DisplayName("record를 찾을 수 없으면 예외가 발생한다.")
        void cannotFindRecordByFileId() {
            //given
            String fileId = "12345";
            given(recordRepository.findByFile_Id(fileId)).willReturn(Optional.empty());

            //when
            //then
            assertThrows(RecordNotFoundException.class, () -> recordService.deleteRecord(fileId));
        }
    }
}