package com.online.cinema.service;

import com.online.cinema.persist.VideoMetadata;
import com.online.cinema.repository.VideoMetadataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
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

    private final VideoMetadataRepository videoMetadataRepository;


    private final Integer PART_POINTS = 1;
    private final Integer DESCRIPTION_POINTS = 2;
    private final Integer COUNTRY_POINTS = 2;
    private final Integer GENRE_POINTS = 2;
    private final Integer YEAR_POINTS = 3;
    private final Integer CREW_POINTS = 4;
    private final Integer NAME_POINTS = 5;

    private final Integer MAX_RESULTS = 10;

    public List<VideoMetadata> find(String condition){
        Map<VideoMetadata, Integer> films = new HashMap<>();
        findFilmsMain(condition, films);

        return films
                .entrySet()
                .stream()
                .sorted(Comparator.comparingInt(a -> a.getKey().getYear_filmed()))
                .sorted((a,b) -> b.getValue().compareTo(a.getValue()))
                .map(e-> {
                    e.getKey().getCrewWithRole().forEach(crewWithRole -> crewWithRole.setVideoMetadata(null));
                    return e.getKey();})
                .limit(MAX_RESULTS)
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
        findByCountry(condition, found, COUNTRY_POINTS);
        findByGenre(condition, found, GENRE_POINTS);
        findByDescription(condition, found, DESCRIPTION_POINTS);

        String[] words = condition.split(" ");

        for (String word: words) {

            if (word.length() < 3) continue;

            findByCrewAnyName(word, found, PART_POINTS);
            findByNameContaining(word, found, PART_POINTS);
            findByCountry(word, found, PART_POINTS);
            findByGenre(word, found, PART_POINTS);
            findByDescription(word, found, PART_POINTS);

        }
    }

    private void findByYear(Integer condition, Map<VideoMetadata, Integer> found, Integer points){
        List<VideoMetadata> videoMetadataByYear = videoMetadataRepository.findVideoMetadataByYear(condition);
        calcPoints(found, videoMetadataByYear, points);
    }

    private void findByName(String condition, Map<VideoMetadata, Integer> found, Integer points){
        List<VideoMetadata> videoMetadataByName = videoMetadataRepository.findAllByName(condition);
        calcPoints(found, videoMetadataByName, points);
    }

    private void findByNameContaining(String condition, Map<VideoMetadata, Integer> found, Integer points){
        List<VideoMetadata> videoMetadataByName = videoMetadataRepository.findAllByNameContaining(condition);
        calcPoints(found, videoMetadataByName, points);
    }

    private void findByCrew(String condition, Map<VideoMetadata, Integer> found, Integer points){
        String[] crew = condition.split(" ");

        if (crew.length != 3 && crew.length != 2) return;

        List<VideoMetadata> videoMetadataByCrew = null;

        if (crew.length == 3){
            videoMetadataByCrew = videoMetadataRepository.findVideoMetadataByCrewFirstLastPatronymic(crew[0], crew[1], crew[2]);
        } else if (crew.length == 2){
            videoMetadataByCrew = videoMetadataRepository.findVideoMetadataByCrewFirstLastPatronymic(crew[0], crew[1], "");
        }

        if (videoMetadataByCrew == null) return;

        calcPoints(found, videoMetadataByCrew, points);
    }

    private void findByCountry(String condition, Map<VideoMetadata, Integer> found, Integer points){
        List<VideoMetadata> videoMetadataByCountry = videoMetadataRepository.findVideoMetadataByCountry(condition);
        calcPoints(found, videoMetadataByCountry, points);
    }

    private void findByGenre(String condition, Map<VideoMetadata, Integer> found, Integer points){
        List<VideoMetadata> videoMetadataByGenre = videoMetadataRepository.findVideoMetadataByGenre(condition);
        calcPoints(found, videoMetadataByGenre, points);
    }

    private void findByDescription(String condition, Map<VideoMetadata, Integer> found, Integer points) {
        List<VideoMetadata> allByDescriptionContaining = videoMetadataRepository.findAllByDescriptionContaining(condition);
        calcPoints(found, allByDescriptionContaining, points);
    }


    private void findByCrewAnyName(String condition, Map<VideoMetadata, Integer> found, Integer points){
        List<VideoMetadata> videoMetadataByCrewFirstOrLastOrPatronymic = videoMetadataRepository.findVideoMetadataByCrewFirstOrLastOrPatronymic(condition);
        calcPoints(found, videoMetadataByCrewFirstOrLastOrPatronymic, points);
    }


    private void calcPoints(Map<VideoMetadata, Integer> found, List<VideoMetadata> videoMetadataList, Integer points){
        Integer mapPoints;
        for (VideoMetadata video: videoMetadataList) {
            mapPoints = found.get(video);
            if (mapPoints == null) mapPoints = 0;
            mapPoints += points;
            found.put(video, mapPoints);
        }
    }


}
