package com.example.senioroslauncher.data.database.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomDatabaseKt;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.senioroslauncher.data.database.entity.EmergencyContactEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class EmergencyContactDao_Impl implements EmergencyContactDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<EmergencyContactEntity> __insertionAdapterOfEmergencyContactEntity;

  private final EntityDeletionOrUpdateAdapter<EmergencyContactEntity> __deletionAdapterOfEmergencyContactEntity;

  private final EntityDeletionOrUpdateAdapter<EmergencyContactEntity> __updateAdapterOfEmergencyContactEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  private final SharedSQLiteStatement __preparedStmtOfClearAllPrimary;

  public EmergencyContactDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfEmergencyContactEntity = new EntityInsertionAdapter<EmergencyContactEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `emergency_contacts` (`id`,`name`,`phoneNumber`,`relationship`,`isPrimary`,`photoUri`,`sortOrder`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final EmergencyContactEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getPhoneNumber());
        statement.bindString(4, entity.getRelationship());
        final int _tmp = entity.isPrimary() ? 1 : 0;
        statement.bindLong(5, _tmp);
        if (entity.getPhotoUri() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getPhotoUri());
        }
        statement.bindLong(7, entity.getSortOrder());
      }
    };
    this.__deletionAdapterOfEmergencyContactEntity = new EntityDeletionOrUpdateAdapter<EmergencyContactEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `emergency_contacts` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final EmergencyContactEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfEmergencyContactEntity = new EntityDeletionOrUpdateAdapter<EmergencyContactEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `emergency_contacts` SET `id` = ?,`name` = ?,`phoneNumber` = ?,`relationship` = ?,`isPrimary` = ?,`photoUri` = ?,`sortOrder` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final EmergencyContactEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getPhoneNumber());
        statement.bindString(4, entity.getRelationship());
        final int _tmp = entity.isPrimary() ? 1 : 0;
        statement.bindLong(5, _tmp);
        if (entity.getPhotoUri() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getPhotoUri());
        }
        statement.bindLong(7, entity.getSortOrder());
        statement.bindLong(8, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM emergency_contacts WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfClearAllPrimary = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE emergency_contacts SET isPrimary = 0";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final EmergencyContactEntity contact,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfEmergencyContactEntity.insertAndReturnId(contact);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final EmergencyContactEntity contact,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfEmergencyContactEntity.handle(contact);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final EmergencyContactEntity contact,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfEmergencyContactEntity.handle(contact);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object setPrimaryContact(final long contactId,
      final Continuation<? super Unit> $completion) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> EmergencyContactDao.DefaultImpls.setPrimaryContact(EmergencyContactDao_Impl.this, contactId, __cont), $completion);
  }

  @Override
  public Object deleteById(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object clearAllPrimary(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearAllPrimary.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfClearAllPrimary.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<EmergencyContactEntity>> getAllContacts() {
    final String _sql = "SELECT * FROM emergency_contacts ORDER BY sortOrder ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"emergency_contacts"}, new Callable<List<EmergencyContactEntity>>() {
      @Override
      @NonNull
      public List<EmergencyContactEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
          final int _cursorIndexOfRelationship = CursorUtil.getColumnIndexOrThrow(_cursor, "relationship");
          final int _cursorIndexOfIsPrimary = CursorUtil.getColumnIndexOrThrow(_cursor, "isPrimary");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
          final int _cursorIndexOfSortOrder = CursorUtil.getColumnIndexOrThrow(_cursor, "sortOrder");
          final List<EmergencyContactEntity> _result = new ArrayList<EmergencyContactEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final EmergencyContactEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpPhoneNumber;
            _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            final String _tmpRelationship;
            _tmpRelationship = _cursor.getString(_cursorIndexOfRelationship);
            final boolean _tmpIsPrimary;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPrimary);
            _tmpIsPrimary = _tmp != 0;
            final String _tmpPhotoUri;
            if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
              _tmpPhotoUri = null;
            } else {
              _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            }
            final int _tmpSortOrder;
            _tmpSortOrder = _cursor.getInt(_cursorIndexOfSortOrder);
            _item = new EmergencyContactEntity(_tmpId,_tmpName,_tmpPhoneNumber,_tmpRelationship,_tmpIsPrimary,_tmpPhotoUri,_tmpSortOrder);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getAllContactsSync(
      final Continuation<? super List<EmergencyContactEntity>> $completion) {
    final String _sql = "SELECT * FROM emergency_contacts ORDER BY sortOrder ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<EmergencyContactEntity>>() {
      @Override
      @NonNull
      public List<EmergencyContactEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
          final int _cursorIndexOfRelationship = CursorUtil.getColumnIndexOrThrow(_cursor, "relationship");
          final int _cursorIndexOfIsPrimary = CursorUtil.getColumnIndexOrThrow(_cursor, "isPrimary");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
          final int _cursorIndexOfSortOrder = CursorUtil.getColumnIndexOrThrow(_cursor, "sortOrder");
          final List<EmergencyContactEntity> _result = new ArrayList<EmergencyContactEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final EmergencyContactEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpPhoneNumber;
            _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            final String _tmpRelationship;
            _tmpRelationship = _cursor.getString(_cursorIndexOfRelationship);
            final boolean _tmpIsPrimary;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPrimary);
            _tmpIsPrimary = _tmp != 0;
            final String _tmpPhotoUri;
            if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
              _tmpPhotoUri = null;
            } else {
              _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            }
            final int _tmpSortOrder;
            _tmpSortOrder = _cursor.getInt(_cursorIndexOfSortOrder);
            _item = new EmergencyContactEntity(_tmpId,_tmpName,_tmpPhoneNumber,_tmpRelationship,_tmpIsPrimary,_tmpPhotoUri,_tmpSortOrder);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getPrimaryContact(final Continuation<? super EmergencyContactEntity> $completion) {
    final String _sql = "SELECT * FROM emergency_contacts WHERE isPrimary = 1 LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<EmergencyContactEntity>() {
      @Override
      @Nullable
      public EmergencyContactEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
          final int _cursorIndexOfRelationship = CursorUtil.getColumnIndexOrThrow(_cursor, "relationship");
          final int _cursorIndexOfIsPrimary = CursorUtil.getColumnIndexOrThrow(_cursor, "isPrimary");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
          final int _cursorIndexOfSortOrder = CursorUtil.getColumnIndexOrThrow(_cursor, "sortOrder");
          final EmergencyContactEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpPhoneNumber;
            _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            final String _tmpRelationship;
            _tmpRelationship = _cursor.getString(_cursorIndexOfRelationship);
            final boolean _tmpIsPrimary;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPrimary);
            _tmpIsPrimary = _tmp != 0;
            final String _tmpPhotoUri;
            if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
              _tmpPhotoUri = null;
            } else {
              _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            }
            final int _tmpSortOrder;
            _tmpSortOrder = _cursor.getInt(_cursorIndexOfSortOrder);
            _result = new EmergencyContactEntity(_tmpId,_tmpName,_tmpPhoneNumber,_tmpRelationship,_tmpIsPrimary,_tmpPhotoUri,_tmpSortOrder);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getContactById(final long id,
      final Continuation<? super EmergencyContactEntity> $completion) {
    final String _sql = "SELECT * FROM emergency_contacts WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<EmergencyContactEntity>() {
      @Override
      @Nullable
      public EmergencyContactEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phoneNumber");
          final int _cursorIndexOfRelationship = CursorUtil.getColumnIndexOrThrow(_cursor, "relationship");
          final int _cursorIndexOfIsPrimary = CursorUtil.getColumnIndexOrThrow(_cursor, "isPrimary");
          final int _cursorIndexOfPhotoUri = CursorUtil.getColumnIndexOrThrow(_cursor, "photoUri");
          final int _cursorIndexOfSortOrder = CursorUtil.getColumnIndexOrThrow(_cursor, "sortOrder");
          final EmergencyContactEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpPhoneNumber;
            _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            final String _tmpRelationship;
            _tmpRelationship = _cursor.getString(_cursorIndexOfRelationship);
            final boolean _tmpIsPrimary;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsPrimary);
            _tmpIsPrimary = _tmp != 0;
            final String _tmpPhotoUri;
            if (_cursor.isNull(_cursorIndexOfPhotoUri)) {
              _tmpPhotoUri = null;
            } else {
              _tmpPhotoUri = _cursor.getString(_cursorIndexOfPhotoUri);
            }
            final int _tmpSortOrder;
            _tmpSortOrder = _cursor.getInt(_cursorIndexOfSortOrder);
            _result = new EmergencyContactEntity(_tmpId,_tmpName,_tmpPhoneNumber,_tmpRelationship,_tmpIsPrimary,_tmpPhotoUri,_tmpSortOrder);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
