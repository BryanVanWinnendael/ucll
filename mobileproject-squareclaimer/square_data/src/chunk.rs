use once_cell::sync::Lazy;
use parking_lot::{
    MappedRwLockReadGuard, MappedRwLockWriteGuard, RwLock, RwLockReadGuard, RwLockWriteGuard,
};
use std::collections::HashMap;
use std::ops::{Index, IndexMut};
use std::sync::Arc;

pub type GridCell = bool;
const CHUNK_SIZE: usize = 16;
pub struct Chunk([[GridCell; CHUNK_SIZE]; CHUNK_SIZE]);

impl Default for Chunk {
    fn default() -> Self {
        Self(Default::default())
    }
}

impl Index<(usize, usize)> for Chunk {
    type Output = GridCell;

    fn index(&self, index: (usize, usize)) -> &Self::Output {
        &self.0[index.0][index.1]
    }
}

impl IndexMut<(usize, usize)> for Chunk {
    fn index_mut(&mut self, index: (usize, usize)) -> &mut Self::Output {
        &mut self.0[index.0][index.1]
    }
}

pub struct ChunkSet(HashMap<(isize, isize), Arc<RwLock<Chunk>>>);

impl ChunkSet {
    pub fn new() -> Self {
        Self(Default::default())
    }
    pub fn get(&mut self, co: (isize, isize)) -> MappedRwLockReadGuard<GridCell> {
        let chunk_co = (
            co.0.div_euclid(CHUNK_SIZE as _),
            co.1.div_euclid(CHUNK_SIZE as _),
        );
        let inner_co = (
            co.0.rem_euclid(CHUNK_SIZE as _) as usize,
            co.1.rem_euclid(CHUNK_SIZE as _) as usize,
        );
        RwLockReadGuard::map(
            self.0
                .entry(chunk_co)
                .or_insert_with(|| get_chunk(chunk_co))
                .read(),
            |c| &c[inner_co],
        )
    }
    pub fn get_mut(&mut self, co: (isize, isize)) -> MappedRwLockWriteGuard<GridCell> {
        let chunk_co = (
            co.0.div_euclid(CHUNK_SIZE as _),
            co.1.div_euclid(CHUNK_SIZE as _),
        );
        let inner_co = (
            co.0.rem_euclid(CHUNK_SIZE as _) as usize,
            co.1.rem_euclid(CHUNK_SIZE as _) as usize,
        );
        RwLockWriteGuard::map(
            self.0
                .entry(chunk_co)
                .or_insert_with(|| get_chunk(chunk_co))
                .write(),
            |c| &mut c[inner_co],
        )
    }
}

static CACHE: RwLock<Lazy<HashMap<(isize, isize), Arc<RwLock<Chunk>>>>> =
    RwLock::new(Lazy::new(HashMap::new));

pub fn get_chunk(co: (isize, isize)) -> Arc<RwLock<Chunk>> {
    if let Some(chunk) = CACHE.read().get(&co).cloned() {
        chunk
    } else {
        CACHE.write().entry(co).or_default().clone()
    }
}
