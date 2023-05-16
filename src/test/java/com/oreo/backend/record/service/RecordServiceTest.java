package com.oreo.backend.record.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.mock;

import com.oreo.backend.file.document.File;
import com.oreo.backend.file.exception.FileNotFoundException;
import com.oreo.backend.file.repository.FileRepository;
import com.oreo.backend.record.document.Record;
import com.oreo.backend.record.dto.response.RecordResponse;
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
    FileRepository fileRepository;

    @Mock
    RecordRepository recordRepository;

    @Nested
    class DeleteRecord {

        @Test
        @DisplayName("Record을 삭제한다.")
        void deleteRecord() {
            //given
            String fileId = "12345";
            Record record = new Record("text", null, null, null, null, mock(File.class));
            given(recordRepository.findByFile_Id(fileId)).willReturn(Optional.of(record));
            willDoNothing().given(recordRepository).delete(record);

            //when
            RecordResponse result = recordService.deleteRecord(fileId);

            //then
            assertThat(result).usingRecursiveComparison().isEqualTo(new RecordResponse(record));
        }

        @Test
        @DisplayName("Record를 찾을 수 없으면 예외가 발생한다.")
        void cannotFindRecordByFileId() {
            //given
            String fileId = "12345";
            given(recordRepository.findByFile_Id(fileId)).willReturn(Optional.empty());

            //when
            //then
            assertThrows(RecordNotFoundException.class, () -> recordService.deleteRecord(fileId));
        }
    }

    @Nested
    class SaveRecord {

        @Test
        @DisplayName("Record를 저장한다.")
        void saveRecord() {
            //given
            String fileId = "12345a";
            String recordId = "aaaa";
            File mockFile = mock(File.class);
            Record mockRecord = mock(Record.class);
            given(fileRepository.findById(fileId)).willReturn(Optional.of(mockFile));
            given(recordRepository.save(any(Record.class))).willReturn(mockRecord);
            given(mockRecord.getId()).willReturn(recordId);

            //when
            String result = recordService.saveRecord(fileId);

            //then
            assertThat(result).isEqualTo(recordId);
        }

        @Test
        @DisplayName("File id로 file을 찾을 수 없으면 예외가 발생한다.")
        void fileNotFoundException() {
            //given
            String fileId = "12345a";
            given(fileRepository.findById(fileId)).willReturn(Optional.empty());

            //when
            //then
            assertThrows(FileNotFoundException.class, () -> recordService.saveRecord(fileId));
        }
    }
}