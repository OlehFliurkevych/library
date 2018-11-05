package fo.project.library.utils;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ObjectModelUtils {

    private static ModelMapper modelMapper;

    static {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    private ObjectModelUtils() {
    }

    // static
    public <D, T> D map(final T entity, Class<D> outClass) {

        return modelMapper.map(entity, outClass);
    }

    // static
    public <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outCLass) {
        return entityList.stream().map(entity -> map(entity, outCLass)).collect(Collectors.toList());
    }

    // static
    public <S, D> D map(final S source, D destination) {
        modelMapper.map(source, destination);
        return destination;
    }


    public <T> Object checkEntity(JpaRepository<T, Long> jpaRepository, long id) {
        if (id <= 0) throw new RuntimeException("Invalid data");
        T entity = null;
        Optional<T> optional = jpaRepository.findById(id);
        if (!optional.isPresent()) {
            throw new RuntimeException("Invalid data, entity don't presented");
        }
        entity = optional.get();
        return entity;
    }
}
