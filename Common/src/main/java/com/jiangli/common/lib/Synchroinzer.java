package com.jiangli.common.lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 *
 * src 一般为open dto
 *
 * dest 一般为数据库dto
 *
 * @author Jiangli
 * @date 2018/2/5 14:51
 */
public abstract class Synchroinzer<SRC_DTO,DEST_DTO,ID> {
    private final List<SRC_DTO> srcList;
    private final List<DEST_DTO> destList;

    public Synchroinzer(List<SRC_DTO> srcList, List<DEST_DTO> destList) {
        if (srcList == null) {
            srcList = new ArrayList<>();
        }
        if (destList == null) {
            destList=new ArrayList<>();
        }
        this.srcList = srcList;
        this.destList = destList;
    }

    public abstract ID extractSrcId(SRC_DTO dto);
    public abstract ID extractDestId(DEST_DTO dto) ;

    public SyncRs sync() {
        SyncRs ret = new SyncRs();
        Map<ID, SRC_DTO> src_map = new HashMap<>();
        Map<ID, DEST_DTO> dest_map = extractMap(destList, dest_dto -> extractDestId(dest_dto));

        srcList.forEach(src_dto -> {
            ID id = extractSrcId(src_dto);

            if (id == null) {
                ret.toBeCreate.add(src_dto);
            } else {
                src_map.put(id,src_dto);

                DEST_DTO dest_dto = dest_map.get(id);
                if (dest_dto != null) {
                    ret.toBeUpdate.put(src_dto, dest_dto);
                }
            }
        });

        destList.forEach(dest_dto -> {
            ID id = extractDestId(dest_dto);
            if (id != null && src_map.get(id) == null) {
                ret.toBeDelete.add(dest_dto);
            }
        });

        return ret;
    }

    private <T> Map<ID, T> extractMap(List<T> list, Function<T,ID> function) {
        Map<ID, T> ret = new HashMap<>();
        if (list != null) {
            for (T t : list) {
                ret.put(function.apply(t),t);
            }
        }
        return ret;
    }

    public class SyncRs{
        private final List<SRC_DTO> toBeCreate=new ArrayList<>();
        private final List<DEST_DTO> toBeDelete=new ArrayList<>();
        private final Map<SRC_DTO,DEST_DTO> toBeUpdate=new HashMap<>();

        public List<SRC_DTO> getToBeCreate() {
            return toBeCreate;
        }

        public SyncRs createProcess(Consumer<SRC_DTO> fun) {
            if ( toBeCreate!=null && toBeCreate.size() > 0) {
                toBeCreate.forEach(fun);
            }
            return this;
        }

        public List<DEST_DTO> getToBeDelete() {
            return toBeDelete;
        }
        public SyncRs deleteProcess(Consumer<DEST_DTO> fun) {
            if ( toBeDelete!=null && toBeDelete.size() > 0) {
                toBeDelete.forEach(fun);
            }
            return this;
        }

        public Map<SRC_DTO, DEST_DTO> getToBeUpdate() {
            return toBeUpdate;
        }
        public SyncRs updateProcess(BiConsumer<SRC_DTO,DEST_DTO> fun) {
            if ( toBeUpdate!=null && toBeUpdate.size() > 0) {
                toBeUpdate.forEach(fun);
            }
            return this;
        }

        @Override
        public String toString() {
            return "SyncRs{" +
                    "toBeCreate=" + toBeCreate +
                    ", toBeDelete=" + toBeDelete +
                    ", toBeUpdate=" + toBeUpdate +
                    '}';
        }
    }
}
