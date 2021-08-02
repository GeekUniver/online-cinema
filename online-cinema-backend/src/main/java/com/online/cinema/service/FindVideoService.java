package com.online.cinema.service;

import com.online.cinema.persist.VideoMetadata;
import com.online.cinema.repository.VideoMetadataFindRepository;
import com.online.cinema.repository.VideoMetadataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Сервис предназначен для поиска видео в БД по входящей строке запроса
 *
 * Критерии поиска следующие:
 *
 * 1. Если условие - четырехзначное число, однозначно ищем по году выпуска фильма
 * 2. Сравниваем условие 1 в 1 с наименованиями фильмов
 * 3. Сравниваем условие со списком съемочной группы (конкатенируем по ФИО или ИФО. Если отчество пустое - не задействуем)
 * 4. Сравниваем условие со списком жанров
 * 5. Сравниваем условие со списком стран
 * 6. Ищем условие в описании фильма
 * 7. Дробим строку на составляющие по пробелу. Если невозможно - поиск окончен.
 * 8. Для каждого полученного условия вызываем поиск с п.1
 *
 * При первичном совпадении начисляем баллы - по году (3 балла), наименованию(5 баллов) или актеру(4 балла)
 * При любом следующем совпадении фильму добавляем 1 балл
 * В итоговом списке пользователю выводим все фильмы, набравшие баллы, от большего количества к меньшему.
 */
@Service
@RequiredArgsConstructor
public class FindVideoService {

    private final VideoMetadataFindRepository videoMetadataFindRepository;


    private final Integer PART_POINTS = 1;
    private final Integer DESCRIPTION_POINTS = 2;
    private final Integer YEAR_POINTS = 3;
    private final Integer CREW_POINTS = 4;
    private final Integer NAME_POINTS = 5;

    public List<VideoMetadata> find(String condition){
        Map<VideoMetadata, Integer> films = new HashMap<>();
        findFilmsMain(condition, films);

        return films
                .entrySet()
                .stream()
                .sorted(Comparator.comparingInt(Map.Entry::getValue))
                .sorted(Comparator.comparingInt(a -> a.getKey().getYear_filmed()))
                .map(e-> {
                    e.getKey().getCrewWithRole().forEach(crewWithRole -> crewWithRole.setVideoMetadata(null));
                    return e.getKey();}
                )
                .collect(Collectors.toList());
    }

    /**
     * Поиск по полному условию
     * @param condition строка поиска
     * @param found найденные фильмы
     */
    protected void findFilmsMain(String condition, Map<VideoMetadata, Integer> found){
        if(condition.length() == 4 && condition.matches("-?\\d+(\\.\\d+)?")){
            findByYear(Integer.parseInt(condition), found, YEAR_POINTS);
        }
        findByCrew(condition, found, CREW_POINTS);
        findByName(condition, found, NAME_POINTS);
    }

    private void findByYear(Integer condition, Map<VideoMetadata, Integer> found, Integer points){
        List<VideoMetadata> videoMetadataByYear = videoMetadataFindRepository.findVideoMetadataByYear(condition);
        Integer mapPoints;
        for (VideoMetadata foundByYear: videoMetadataByYear) {
            mapPoints = found.get(foundByYear);
            if (mapPoints == null) mapPoints = 0;
            mapPoints +=points;
            found.put(foundByYear, mapPoints);
        }
    }

    private void findByName(String condition, Map<VideoMetadata, Integer> found, Integer points){
        List<VideoMetadata> videoMetadataByName = videoMetadataFindRepository.findAllByName(condition);
        Integer mapPoints;
        for (VideoMetadata foundByName: videoMetadataByName) {
            mapPoints = found.get(foundByName);
            if (mapPoints == null) mapPoints = 0;
            mapPoints +=points;
            found.put(foundByName, mapPoints);
        }
    }

    private void findByCrew(String condition, Map<VideoMetadata, Integer> found, Integer points){
        String[] crew = condition.split(" ");

        if (crew.length != 3 && crew.length != 2) return;

        List<VideoMetadata> videoMetadataByCrew = null;

        if (crew.length == 3){
            videoMetadataByCrew = videoMetadataFindRepository.findVideoMetadataByCrewFirstLastPatronymic(crew[0], crew[1], crew[2]);
        } else if (crew.length == 2){
            videoMetadataByCrew = videoMetadataFindRepository.findVideoMetadataByCrewFirstLastPatronymic(crew[0], crew[1], "");
        }

        if (videoMetadataByCrew == null) return;

        Integer mapPoints;
        for (VideoMetadata foundByCrew: videoMetadataByCrew) {
            mapPoints = found.get(foundByCrew);
            if (mapPoints == null) mapPoints = 0;
            mapPoints +=points;
            found.put(foundByCrew, mapPoints);
        }
    }

}
