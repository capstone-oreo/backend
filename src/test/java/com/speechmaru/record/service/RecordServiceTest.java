package com.speechmaru.record.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.mock;

import com.speechmaru.file.document.File;
import com.speechmaru.file.exception.FileNotFoundException;
import com.speechmaru.file.repository.FileRepository;
import com.speechmaru.record.document.Record;
import com.speechmaru.record.dto.response.RecordResponse;
import com.speechmaru.record.exception.RecordNotFoundException;
import com.speechmaru.record.repository.RecordRepository;
import java.util.List;
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
            Record record = new Record(List.of("text"), null, null, null, null, mock(File.class));
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
            String result = recordService.saveRecord(fileId, List.of("hello"));

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
            assertThrows(FileNotFoundException.class, () -> recordService.saveRecord(fileId, List.of()));
        }
    }

    @Nested
    class FindRecord {

        @Test
        @DisplayName("FileId로 record를 조회한다.")
        void findRecord() {
            //given
            File mockFile = mock(File.class);
            Record record = Record.builder()
                .text(List.of("a", "b"))
                .speed(List.of(1, 2, 3))
                .volume(List.of(3, 3, 3))
                .habitualWord(List.of("hello"))
                .file(mockFile).build();
            String fileId = "abcde123";

            given(recordRepository.findByFile_Id(fileId)).willReturn(Optional.of(record));

            //when
            RecordResponse result = recordService.findRecord(fileId);

            //then
            assertThat(result).usingRecursiveComparison().isEqualTo(new RecordResponse(record));
        }

        @Test
        @DisplayName("fileId로 record를 찾을 수 없으면 예외가 발생한다.")
        void recordNotFoundByFileId() {
            //given
            String fileId = "abcde123";

            given(recordRepository.findByFile_Id(fileId)).willReturn(Optional.empty());

            //when
            //then
            assertThrows(RecordNotFoundException.class, () -> recordService.findRecord(fileId));

        }
    }
}